package com.amit.rest.restfulwebservices.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJPARepository extends JpaRepository<User, Integer> {
}
