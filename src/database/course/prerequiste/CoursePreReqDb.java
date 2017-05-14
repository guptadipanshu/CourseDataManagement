package database.course.prerequiste;

import java.util.List;

public interface CoursePreReqDb {
	void createCoursePreReqTable();
	void addPrerequiste(String courseID,String preReq);

	List<String> getPrerequiste(String courseID);

    void removePreReq(String courseID, String preRequisteCourseId);
}
