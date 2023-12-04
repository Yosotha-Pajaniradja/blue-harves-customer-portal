package com.blue.harvest.customer.management.api.infra.jpa.repository;



import com.blue.harvest.customer.management.api.infra.jpa.entity.CustomerEntity;
import com.blue.harvest.customer.management.api.domain.CustomerDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, CustomerDomain> {
    CustomerEntity findCustomerEntitiesByIdCustomer(final String idCustomer);

}
