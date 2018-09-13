package com.joakorelhelou.orm;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class HibernateOrmRelationshipsTest extends HibernateTest {

    public HibernateOrmRelationshipsTest() {
        super(H2_CONFIG_FILE, Instructor.class, InstructorDetail.class, Course.class);
    }

    @Test
    public void oneToOneRelationship() {
        Instructor instructor = Instructor.createDummy();
        InstructorDetail detail = InstructorDetail.createDummy();
        instructor.setInstructorDetail(detail);

        Long id = saveEntity(instructor);
        assertNotNull(id);

        Instructor persistedInstructor = queryById(Instructor.class, id);
        assertThat(persistedInstructor.getId(), is(id));
        assertNotNull(persistedInstructor.getInstructorDetail());
        Long detailId = persistedInstructor.getInstructorDetail().getId();
        assertNotNull(detailId);

        //Deleting Instructor will also delete Instructor detail
        delete(persistedInstructor);

        assertNull(queryById(InstructorDetail.class, detailId));
        assertNull(queryById(Instructor.class, id));

    }

    @Test
    public void oneToOneBiRelationship() {
        Instructor instructor = Instructor.createDummy();
        InstructorDetail detail = InstructorDetail.createDummy();
        instructor.setInstructorDetail(detail);

        Long id = saveEntity(instructor);
        assertNotNull(id);

        Instructor persistedInstructor = queryById(Instructor.class, id);
        assertThat(persistedInstructor.getId(), is(id));
        assertNotNull(persistedInstructor.getInstructorDetail());
        Long detailId = persistedInstructor.getInstructorDetail().getId();
        assertNotNull(detailId);
        assertNotNull(persistedInstructor.getInstructorDetail().getInstructor());

        //Deleting Instructor Detail will also delete Instructor (1-1 bi relationship)
        delete(persistedInstructor.getInstructorDetail());

        assertNull(queryById(InstructorDetail.class, detailId));
        assertNull(queryById(Instructor.class, id));

    }


    @Test
    public void oneToManyBiRelationship() {
        //Add an instructor with details
        Instructor instructor = Instructor.createDummy();
        InstructorDetail detail = InstructorDetail.createDummy();
        instructor.setInstructorDetail(detail);

        Long instructorId = saveEntity(instructor);
        assertNotNull(instructorId);

        //Create and add some courses
        instructor = queryById(Instructor.class, instructorId);
        Course course1 = new Course("Air Guitar - The ultimate guide");
        Course course2 = new Course("Paintball - The ultimate guide");
        course1.setInstructor(instructor);
        course2.setInstructor(instructor);

        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        instructor.setCourses(courses);

        //Assert the courses were saved
        Long courseId1 = saveEntity(course1);
        Long courseId2 = saveEntity(course2);
        assertNotNull(courseId1);
        assertNotNull(courseId2);

        //Verify that the instructor Ids for the courses are the correct ones
        course1 = queryById(Course.class, courseId1);
        course2 = queryById(Course.class, courseId2);
        assertThat(course1.getInstructor().getId(), is(instructorId));
        assertThat(course2.getInstructor().getId(), is(instructorId));

        //List of courses from instructor is the same than created
        assertEquals(courses, instructor.getCourses());

        //Deleting a course should not delete the instructor
        delete(course1);

        Instructor refreshedInstructor = queryById(Instructor.class, instructorId);

        //Instructor still exists
        assertNotNull(refreshedInstructor);
        //course no longer exists
        assertFalse(refreshedInstructor.getCourses().contains(course1));

    }

    @Test(expected = ConstraintViolationException.class) //Test @Coulumn(unique = true)
    public void cannotAddSameNameCourses(){
        Course course1 = new Course("Air Guitar - The ultimate guide");
        Course course2 = new Course("Air Guitar - The ultimate guide");
        saveEntity(course1);
        saveEntity(course2);
    }

    @AfterClass
    public static void tearDown() {
        out.println("Running tear down method");
        closeConn();
    }

}
