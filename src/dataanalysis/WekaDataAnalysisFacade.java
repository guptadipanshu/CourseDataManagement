package dataanalysis;

import database.DbHelper;
import database.course.CourseContract;
import database.course.instructor.CourseInstructorContract;
import database.course.student.CourseStudentContract;
import database.course.student.CourseStudentDbImpl;
import database.student.StudentContract;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import weka.associations.Apriori;
import weka.core.*;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.converters.Loader;
import weka.experiment.InstanceQuery;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jpolino on 4/22/2017.
 */
public class WekaDataAnalysisFacade implements DataAnalysisFacade {

    Instances allInstanceData = null;
    Apriori aprioriModel;

    @Override
    public void prepareModel() {
        try {
            //ResultSet courseStudentEnrollments = DbHelper.getInstance().executeQuery(CourseStudentContract.SELECT_COURSE_STUDENT_ENROLLMENTS_FORANALYSIS);
            CourseStudentDbImpl courseStudentDb = new CourseStudentDbImpl();
            ResultSet allCourses = DbHelper.getInstance().executeQuery(CourseContract.SELECT_ALL_COURSE);
            ResultSet allStudents = DbHelper.getInstance().executeQuery(StudentContract.QUERY_ALL_STUDENT);
            if (allCourses == null || allStudents == null) {
                return;
            }

            try {
                ArrayList<Integer> courseIds = new ArrayList<Integer>();
                FastVector allAttributes = new FastVector();
                FastVector nominalVal = new FastVector(2);
                nominalVal.addElement("taken");
                nominalVal.addElement("none");

                while (allCourses.next()) {
                    String courseId = allCourses.getString(CourseContract.ID);
                    if(!courseIds.contains(Integer.parseInt(courseId))){
                        courseIds.add(Integer.parseInt(courseId));
                        Attribute courseAttribute = new Attribute("Course" + courseId , nominalVal);
                        allAttributes.addElement(courseAttribute);
                    }
                }

                allStudents.last();
                Instances trainingSet = new Instances("Courses Taken By Students", allAttributes, allStudents.getRow());
                allStudents.beforeFirst();
                while(allStudents.next()){
                    try {
                        Instance studentInstance = new DenseInstance(courseIds.size());
                        String studentId = allStudents.getString(StudentContract.UDID);
                        List<CourseStudentDbImpl.Record> studentRecords = courseStudentDb.getRecordAllTerms(studentId);
                        int index = 0;
                        for(Integer courseId : courseIds){
                            Boolean courseTaken = false;
                            for(CourseStudentDbImpl.Record studentRecord : studentRecords){
                                if(Integer.parseInt(studentRecord.courseID) == courseId){
                                    courseTaken = true;
                                }
                            }
                            studentInstance.setValue((Attribute)allAttributes.elementAt(index), courseTaken ? "taken" : "none");
                            ++index;
                        }
                        trainingSet.add(studentInstance);
                    }
                    catch(SQLException e){
                        e.printStackTrace();
                    }
                }
                ArffSaver saver = new ArffSaver();
                saver.setInstances(trainingSet);
                saver.setFile(new File("./weka_arff/StudentCourses.arff"));
                saver.writeBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    allCourses.close();
                    allStudents.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String executeModelAnalysis() {
        try {
            aprioriModel = new Apriori();
            Instances data = DataSource.read("./weka_arff/StudentCourses.arff");
            aprioriModel.buildAssociations(data);

            System.out.println(aprioriModel);
        } catch (Exception e){
            e.printStackTrace();
        }
        return aprioriModel.toString();
    }

}
