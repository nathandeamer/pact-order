package com.nathandeamer.orders;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<CustomerOrder, Integer> {
}
