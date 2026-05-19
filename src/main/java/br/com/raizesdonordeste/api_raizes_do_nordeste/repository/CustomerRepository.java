package br.com.raizesdonordeste.api_raizes_do_nordeste.repository;

import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
