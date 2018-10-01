package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Place;
import ar.edu.itba.paw.models.PremiumUser;
import ar.edu.itba.paw.models.User;
import org.joda.time.format.DateTimeFormat;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class PremiumUserJdbcDaoTest {

        private static final String FIRSTNAME = "first_name";
        private static final String LASTNAME = "last_name";
        private static final String EMAIL = "email";
        private static final String USERNAME = "username";
        private static final String CELLPHONE = "cellphone";
        private static final String BIRTHDAY = "1994-12-26";
        private static final String COUNTRY = "country";
        private static final String STATE = "state";
        private static final String CITY = "city";
        private static final String STREET = "street";
        private static final int REPUTATION = 10;
        private static final String PASSWORD = "password";
        private static final long   USERID = 14;
        private static final String EXISTANT_USERNAME = "ExistantUsername";
        private static final String NONEXISTANT_USERNAME = "NonExistantUsername";

        @Autowired
        private DataSource dataSource;

        @Autowired
        private PremiumUserJdbcDao premiumUserDao;


        private JdbcTemplate jdbcTemplate;

        @Before
        public void setUp() {
            jdbcTemplate = new JdbcTemplate(dataSource);
            JdbcTestUtils.deleteFromTables(jdbcTemplate, "accounts");
        }

        private void insertUser(String userName) {
            jdbcTemplate.execute(("DELETE FROM users WHERE userId = " + USERID));
            jdbcTemplate.execute("INSERT INTO users (firstname, lastname, email, userid)" +
                    " VALUES ('" + FIRSTNAME + "' , '" + LASTNAME + "', '" + EMAIL + "', " + USERID + ");");

            jdbcTemplate.execute("INSERT INTO accounts (username, email, cellphone, birthday," +
                    " country, state, city, street, reputation, password, userId)" +
                        " VALUES ('" + userName + "' , '" + EMAIL + "', '" + CELLPHONE + "', '" + BIRTHDAY + "', '" +
                    COUNTRY + "', '" + STATE + "', '" + CITY + "', '" + STREET + "', " + REPUTATION +
                    ", '" + PASSWORD +"', " + USERID +");");
        }

        private void insertRole(final int roleId, final String roleName) {
            jdbcTemplate.execute("INSERT INTO roles (roleId, roleName) VALUES(" + roleId + ", '" + roleName + "')");
        }

        @Test
        public void testCreate() {
            //exercise class
            final PremiumUser user = premiumUserDao.create(FIRSTNAME, LASTNAME, EMAIL, USERNAME,
            CELLPHONE, BIRTHDAY, COUNTRY, STATE, CITY, STREET, REPUTATION, PASSWORD).get();

            //postconditions
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            Assert.assertNotNull(user);
            Assert.assertEquals(FIRSTNAME, user.getFirstName());
            Assert.assertEquals(USERNAME, user.getUserName());
            Assert.assertEquals(LocalDate.parse(BIRTHDAY, formatter), user.getBirthday());
            Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "accounts"));
        }

        @Test
        public void testFindByUserNameWithExistentId() {
            //set up
            insertUser(EXISTANT_USERNAME);

            //exercise class
            final Optional<PremiumUser> returnedUser = premiumUserDao.findByUserName(EXISTANT_USERNAME);

            //postconditions
            Assert.assertTrue(returnedUser.isPresent());
            final PremiumUser user = returnedUser.get();
            Assert.assertEquals(EXISTANT_USERNAME, user.getUserName());
        }

        @Test
        public void testFindByUserNameWithNonExistentId() {
            //exercise class
            final Optional<PremiumUser> returnedUser = premiumUserDao.findByUserName(NONEXISTANT_USERNAME);

            //postconditions
            Assert.assertTrue(!returnedUser.isPresent());
        }

        @Test
        public void testRemoveNonExistentUser(){
            //exercise class
            boolean returnValue = premiumUserDao.remove(NONEXISTANT_USERNAME);

            //postconditions
            Assert.assertEquals(false, returnValue);
        }

        @Test
        public void testRemoveExistentUser(){
            //set up
            insertUser(EXISTANT_USERNAME);

            //exercise class
            boolean returnValue = premiumUserDao.remove(EXISTANT_USERNAME);

            //postconditions
            Assert.assertEquals(true, returnValue);
            Assert.assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate,
                    "accounts"));
        }

        @Test
        public void testUpdateUserInfo(){
            //set up
            insertUser(EXISTANT_USERNAME);
            final String newUserName = "newUserName";
            final String newPassword = "newPassword";
            final String newBirthday = "2000-05-05";

            //exercise class
            PremiumUser modifyUser = premiumUserDao.updateUserInfo(FIRSTNAME, LASTNAME, EMAIL,
                    newUserName, CELLPHONE, newBirthday, COUNTRY, STATE, CITY, STREET, REPUTATION,
                    newPassword, EXISTANT_USERNAME).get();

            //postconditions
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            Assert.assertEquals(LocalDate.parse(newBirthday, formatter), modifyUser.getBirthday());
            Assert.assertEquals(newUserName, modifyUser.getUserName());
            Assert.assertEquals(newPassword, modifyUser.getPassword());
            Assert.assertEquals(CELLPHONE, modifyUser.getCellphone());
        }

        @Test
        public  void testFindByEmailExistentUser() {
            //set up
            insertUser(EXISTANT_USERNAME);

            //exercise class
            final Optional<PremiumUser> returnedUser = premiumUserDao.findByEmail(EMAIL);

            //postconditions
            Assert.assertTrue(returnedUser.isPresent());
            final PremiumUser user = returnedUser.get();
            Assert.assertEquals(EMAIL, user.getEmail());

        }

    @Test
    public  void testFindByEmailNonExistentUser() {

        //exercise class
        final Optional<PremiumUser> returnedUser = premiumUserDao.findByEmail(EMAIL);

        //postconditions
        Assert.assertTrue(!returnedUser.isPresent());

    }

    @Test
    public void testaddRole() {
        //set up
        insertUser(EXISTANT_USERNAME);
        insertRole(0,"ROLE_USER");

        //exercise class
        final boolean returnedValue = premiumUserDao.addRole(EXISTANT_USERNAME, 0);

        //postconditions
        Assert.assertTrue(returnedValue);

    }
}


