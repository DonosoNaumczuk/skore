package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PremiumUserDao {

    public Optional<PremiumUser> findByUserName(final String userName);

    public Optional<PremiumUser> create(final String firstName, final String lastName,
                                        final String email, final String userName,
                                        final String cellphone, final String birthday,
                                        final String country, final String state, final String city,
                                        final String street, final int reputation, final String password,
                                        final MultipartFile file) throws IOException;

    public boolean remove(final String userName);

    public Optional<byte[]> readImage(final String userName);

    public Optional<PremiumUser> updateUserInfo(final String newFirstName, final String newLastName,
                                                final String newEmail,final String newUserName,
                                                final String newCellphone, final String newBirthday,
                                                final String newCountry, final String newState,
                                                final String newCity, final String newStreet,
                                                final int newReputation, final String newPassword,
                                                final String oldUserName);


    public Optional<PremiumUser> findByEmail(final String email);

    public boolean addRole(final String username, final int roleId);

    public boolean enableUser(final String username, final String code);

    public Set<Role> getRoles(final String username);

    public boolean removeRole(final String username, final int roleId);

//    public List<Sport> getSports(final String username);
//
//    public boolean addSport(final String username, String sportName);
//
//    public boolean removeSport(final String username, String sportName);


}

