package clovers1111.awsweek2practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
class OrderRepository extends JpaRepository<Order, Long> {

    @Transactional
    public createOrder() {

    }
}
