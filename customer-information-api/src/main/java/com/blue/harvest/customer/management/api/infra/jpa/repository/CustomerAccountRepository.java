package com.blue.harvest.customer.management.api.infra.jpa.repository;



import com.blue.harvest.customer.management.api.domain.CustomerDomain;
import com.blue.harvest.customer.management.api.infra.jpa.entity.CustomerAccountEntity;
import com.blue.harvest.customer.management.api.infra.jpa.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAccountRepository extends JpaRepository<CustomerAccountEntity, CustomerDomain> {

}
