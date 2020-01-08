package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.JWTDao;
import ar.edu.itba.paw.interfaces.JWTService;
import ar.edu.itba.paw.models.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JWTServiceImpl implements JWTService {

    @Autowired
    JWTDao jwtDao;

    public JWTServiceImpl() {

    }

    @Override
    public boolean isInBlacklist(String jwtoken) {
        return jwtDao.isInBlacklist(jwtoken);
    }

    @Override
    public JWT addBlacklist(String jwtoken, LocalDateTime expiry) {
        return jwtDao.addBlacklist(jwtoken, expiry);
    }

    @Override
    public void delete(JWT jwt) {
        jwtDao.delete(jwt);
    }

    @Override
    public List<JWT> getAll() {
        return jwtDao.getAll();
    }
}