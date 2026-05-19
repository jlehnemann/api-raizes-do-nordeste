package br.com.raizesdonordeste.api_raizes_do_nordeste.repository;

import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long> {
}
