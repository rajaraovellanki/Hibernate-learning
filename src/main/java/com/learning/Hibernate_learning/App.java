package com.learning.Hibernate_learning;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.learning.Hibernate_learning.relations.Laptop;
import com.learning.Hibernate_learning.relations.Student;

public class App 
{
    public static void main( String[] args )
    {
    	jpaSpecificationTest();    	
    }
    
    public static void jpaSpecificationTest()
    {
    	//Use EntityManager object instead of Session object
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPersistence");
    	EntityManager em = emf.createEntityManager();
    	
    	//use em.find() instead of session.get() for fetching the values from the DB
    	Laptop laptop = em.find(Laptop.class, 101);
    	
    	//printing the object
    	System.out.println(laptop);
    	
    	Laptop laptop2 = new Laptop();
    	laptop2.setlId(102);
    	laptop2.setName("Sony");
    	laptop2.setPrice(599.99);
    	
    	em.getTransaction().begin();    	
    	//user em.persist(obj) instead of session.save(obj) to save the objects in the DB
    	em.persist(laptop2);
    	
    	em.getTransaction().commit();    	    	
    }
    
    public static void loadVsGet()
    {
    	Configuration con = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Laptop.class);    	
    	SessionFactory sf = con.buildSessionFactory();    	
    	Session session = sf.openSession();    	
    	session.beginTransaction();
    	    	
    	Laptop laptop = session.load(Laptop.class, 101);
    	
    	System.out.println(laptop);
    	System.out.println(laptop);
    	System.out.println(laptop);
    	
    	laptop = session.get(Laptop.class, 101);
    	
    	System.out.println(laptop);
    	System.out.println(laptop);
    	System.out.println(laptop);
    	
    	session.getTransaction().commit();
    	session.close();
    	sf.close();
    }
    
    public static void typesOfStates()
    {    	
    	Configuration con = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Laptop.class);    	
    	SessionFactory sf = con.buildSessionFactory();    	
    	Session session = sf.openSession();    	
    	session.beginTransaction();
    	    	
    	Laptop laptop = new Laptop();
    	
    	//Transient state
    	laptop.setlId(101);
    	laptop.setName("Dell");
    	laptop.setPrice(499.99);
    	
    	//Persistent state
    	session.save(laptop);
    	
    	laptop.setPrice(399.99);	//value will be updated to 399.99 in the DB
    	
    	//Removed state
    	//session.remove(laptop);
    	
    	session.getTransaction().commit();
    	//Detached state
    	session.detach(laptop);
    	
    	laptop.setPrice(299.99);	//value will still be 399.99 in the DB as we detached
    	
    	session.close();
    	sf.close();
    }
    
    public static void sqlQueries()
    {
    	Configuration con = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class);    	
    	SessionFactory sf = con.buildSessionFactory();    	
    	Session session = sf.openSession();    	
    	session.beginTransaction();
    	
    	SQLQuery sqlQuery = null;
    	
    	//SELECT * FROM student WHERE marks>60
    	System.out.println("===========QUERY 8===========");
    	sqlQuery = session.createSQLQuery("SELECT * FROM student WHERE marks>60");
    	sqlQuery.addEntity(Student.class);
    	List<Student> students1 = sqlQuery.list();
    	students1.forEach(System.out::println);
    	
    	//SELECT name,marks FROM student WHERE marks>60
    	System.out.println("===========QUERY 9===========");
    	sqlQuery = session.createSQLQuery("SELECT name,marks FROM student WHERE marks>60");
    	sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
    	List<Object> students2 = sqlQuery.list();
    	students2.forEach(student-> {
    		Map keyValue = (Map)student;
    		System.out.println(keyValue.get("name")+" : "+keyValue.get("marks"));
    		});
    	
    	session.getTransaction().commit();
    	session.close();
    	sf.close();
    }
    
    public static void complexHqlQueries()
    {
    	Configuration con = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class);    	
    	SessionFactory sf = con.buildSessionFactory();    	
    	Session session = sf.openSession();    	
    	session.beginTransaction();    	   	
    	
    	Query query = null;
    	
    	// SELECT sid,name,marks FROM student WHERE sid=9;
    	System.out.println("===========QUERY 4===========");
    	query = session.createQuery("SELECT sid,name,marks FROM Student WHERE sid=10");
    	Object[] stu2 = (Object[])query.uniqueResult();    	
    	System.out.println("Sid: "+stu2[0]+", Name: "+stu2[1]+", Marks: "+stu2[2]);
    	
    	// SELECT sid,marks FROM student;
    	System.out.println("===========QUERY 5===========");
    	query = session.createQuery("SELECT sid,marks FROM Student");
    	List<Object[]> students1 = query.list();  
    	students1.forEach(student -> System.out.println("Sid: "+student[0]+", Marks: "+student[1]));
    	
    	// SELECT SUM(marks) FROM student s WHERE s.marks>60;
    	System.out.println("===========QUERY 6===========");
    	query = session.createQuery("SELECT SUM(marks) FROM Student s WHERE s.marks>60");
    	Long sumOfMarks = (Long)query.uniqueResult();  
    	System.out.println(sumOfMarks);
    	
    	// SELECT SUM(marks) FROM student s WHERE s.marks>?;    	
    	System.out.println("===========QUERY 7===========");
    	query = session.createQuery("SELECT SUM(marks) FROM Student s WHERE s.marks> :param");
    	int thresholdValue = 80;
    	query.setParameter("param", thresholdValue);
    	Long sumOfMarks2 = (Long)query.uniqueResult();
    	System.out.println(sumOfMarks2);
    	    	
    	session.getTransaction().commit();
    	session.close();
    	sf.close();
    }
    
    public static void simpleHqlQueries()
    {
    	Configuration con = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class);    	
    	SessionFactory sf = con.buildSessionFactory();    	
    	Session session = sf.openSession();    	
    	session.beginTransaction();    	   	
    	
    	Query query = null;
    	    	
    	// SELECT * FROM student;
    	System.out.println("===========QUERY 1===========");
    	query = session.createQuery("FROM Student");
    	List<Student> stuList1 = query.list();    	
    	stuList1.forEach(System.out::println);  
    	
    	// SELECT * FROM student WHERE marks>50;
    	System.out.println("===========QUERY 2===========");
    	query = session.createQuery("FROM Student WHERE marks>50");
    	List<Student> stuList2 = query.list();    	
    	stuList2.forEach(System.out::println);  
    	
    	// SELECT * FROM student WHERE sid=9;
    	System.out.println("===========QUERY 3===========");
    	query = session.createQuery("FROM Student WHERE sid=9");
    	Student stu1 = (Student)query.uniqueResult();    	
    	System.out.println("Sid: "+stu1.getSid()+", Name: "+stu1.getName()+", Marks: "+stu1.getMarks());  
    	
    	session.getTransaction().commit();
    	session.close();
    	sf.close();
    }
    
    public static void insertStudentIntoDB()
    {
    	Configuration con = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class);    	
    	SessionFactory sf = con.buildSessionFactory();    	
    	Session session = sf.openSession();    	
    	session.beginTransaction();    	
    	
    	Random random = new Random();
    	for (int i=1; i<=50; i++)
    	{
    		Student stu = new Student();
    		stu.setSid(i);
    		stu.setName("Stu-"+i);
    		stu.setMarks(random.nextInt(100));
    		session.save(stu);
    	}
    	
    	session.getTransaction().commit();
    	session.close();
    	sf.close();
    }    
    
    public static void ehCacheQueryExample()
    {
    	Person person=null;
        
        Configuration con = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Person.class);
        
        SessionFactory sf = con.buildSessionFactory();
        
        Session session1 = sf.openSession();                
        
        session1.beginTransaction();
        Query query1 = session1.createQuery("from person where id=1");
        query1.setCacheable(true);
        person = (Person) query1.uniqueResult();
        session1.getTransaction().commit();
        session1.close();
        System.out.println(person);
        
        Session session2 = sf.openSession();
        
        session2.beginTransaction();
        Query query2 = session2.createQuery("from person where id=1");
        query2.setCacheable(true);
        person = (Person)query2.uniqueResult();
        session2.getTransaction().commit();        
        session2.close();                
        System.out.println(person);
        
        sf.close();
    }
    
    public static void ehCacheExample()
    {
    	Person person=null;
        
        Configuration con = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Person.class);
        
        SessionFactory sf = con.buildSessionFactory();
        
        Session session1 = sf.openSession();                
        
        session1.beginTransaction();
        person = (Person)session1.get(Person.class, 1);
        session1.getTransaction().commit();
        session1.close();
        System.out.println(person);
        
        Session session2 = sf.openSession();
        
        session2.beginTransaction();        
        person = (Person)session2.get(Person.class, 1);
        session2.getTransaction().commit();        
        session2.close();                
        System.out.println(person);
        
        sf.close();
    }
    
    public static void testFirstLevelCache()
    {
    	Person person=null;
        
        Configuration con = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Person.class);
        
        SessionFactory sf = con.buildSessionFactory();
        
        Session session = sf.openSession();                
        
        session.beginTransaction();
        
        person = (Person)session.get(Person.class, 1);
        System.out.println(person);
        
        
        person = (Person)session.get(Person.class, 1);
        System.out.println(person);
        
        session.getTransaction().commit();
        session.close();        
        
        sf.close();
    }
    
    public static void insertRelationsIntoDB()
    {   	
    	Laptop laptop = new Laptop();
    	laptop.setlId(101);
    	laptop.setName("Dell");
    	
    	Student student = new Student();
    	student.setSid(1);
    	student.setName("Raja");
    	student.setMarks(50);
    	//student.setLaptop(Arrays.asList(new Laptop[]{laptop}));
    	
    	//laptop.setStudent(Arrays.asList(new Student[]{student}));
    	
    	Configuration con = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
    			.addAnnotatedClass(Laptop.class);
    	SessionFactory sf = con.buildSessionFactory();
    	
    	Session session = sf.openSession();
    	
    	Transaction tx = session.beginTransaction();
    	
    	session.save(student);
    	
    	session.save(laptop);
    	
    	tx.commit();
    }    
    
    public static Person getPersonFromDB()
    {
    	Person person = null;
        
        Configuration con = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Person.class);
        
        SessionFactory sf = con.buildSessionFactory();
        
        Session session = sf.openSession();  
        
        Transaction tx = session.beginTransaction();
        
        person = (Person)session.get(Person.class, 1);
        
        tx.commit();
        
        return person;
    }
    
    public static void insertPersonIntoDB()
    {
    	PersonName pname = new PersonName();
    	pname.setFirstName("Anusha");
    	pname.setMiddleNmae("");
    	pname.setLastName("Kolla");
    	
        Person person = new Person();
        person.setId(2);
        person.setName(pname);
        person.setAge(28);
        
        Configuration con = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Person.class);
        
        SessionFactory sf = con.buildSessionFactory();
        
        Session session = sf.openSession();  
        
        Transaction tx = session.beginTransaction();
        
        session.save(person);
        
        tx.commit();
        
        System.out.println(person);
    }
}
