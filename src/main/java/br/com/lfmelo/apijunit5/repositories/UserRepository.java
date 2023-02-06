package br.com.lfmelo.apijunit5.repositories;

import br.com.lfmelo.apijunit5.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
