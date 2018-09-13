package com.joakorelhelou.orm;

import org.junit.*;

import java.util.List;

import static java.lang.String.format;
import static java.lang.System.out;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class HibernateOrmTest extends HibernateTest {

    public HibernateOrmTest() {
        super(H2_CONFIG_FILE, Student.class);
    }

    @Test
    public void persistEntity() {
        Long id = saveEntity(Student.createDummy());
        assertNotNull(id);

        Student persistedStudent = queryById(Student.class, id);
        assertThat(persistedStudent.getId(), is(id));
        assertThat(persistedStudent.getName(), is("Paul"));
    }

    @Test
    public void queryEntities() {
        Long id = saveEntity(Student.createDummy());
        assertNotNull(id);

        List<Student> students = query("from Student");
        assertTrue(students.size() > 0);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void queryEntitiesFiltering() {
        Long id = saveEntity(Student.createDummy());
        assertNotNull(id);

        List<Student> students = query("from Student s where s.lastName='Wall'");
        assertTrue(students.size() > 0);
        for (Student student : students) {
            assertThat(student.getLastName(), is("Wall"));
        }

        students = query("from Student s where s.lastName='Wall' AND s.name='Paul'");
        assertTrue(students.size() > 0);
        for (Student student : students) {
            assertThat(student.getName(), is("Paul"));
            assertThat(student.getLastName(), is("Wall"));
        }
    }

    @Test
    public void updateEntity() {
        Long id = saveEntity(Student.createDummy());
        assertNotNull(id);

        Student persistedStudent = queryById(Student.class, id);
        assertThat(persistedStudent.getId(), is(id));

        //Updating with Query
        updateQuery(format("update Student set first_name='Scooby' WHERE id=%s", id));

        persistedStudent = queryById(Student.class, id);
        assertThat(persistedStudent.getName(), is("Scooby"));

        persistedStudent.setName("Carlos");

        //updating entity
        update(persistedStudent);

        persistedStudent = queryById(Student.class, id);
        assertThat(persistedStudent.getName(), is("Carlos"));
    }

    @Test
    public void deleteEntity() {
        Long id = saveEntity(Student.createDummy());
        assertNotNull(id);

        Student persistedStudent = queryById(Student.class, id);
        assertThat(persistedStudent.getId(), is(id));

        //deleting entity
        delete(persistedStudent);

        persistedStudent = queryById(Student.class, id);
        assertNull(persistedStudent);

        id = saveEntity(Student.createDummy());
        assertNotNull(id);

        //delete entity using update
        updateQuery(format("delete from Student WHERE id=%s", id));

        persistedStudent = queryById(Student.class, id);
        assertNull(persistedStudent);

    }

    @AfterClass
    public static void tearDown(){
        out.println("Running tear down method");
        closeConn();
    }


}
