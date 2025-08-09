package com.sudagoarth.user_service.service;

import com.sudagoarth.user_service.model.User;
import com.sudagoarth.user_service.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
  private final UserRepository repo;
  public UserService(UserRepository repo){ this.repo = repo; }

  public List<User> all(){ return repo.findAll(); }
  public User create(User u){ return repo.save(u); }
  public User get(Long id){ return repo.findById(id).orElse(null); }
  public void delete(Long id){ repo.deleteById(id); }
}
