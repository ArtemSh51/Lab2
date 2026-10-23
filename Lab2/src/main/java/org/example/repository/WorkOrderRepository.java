package org.example.repository;

import org.example.entity.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {
    long countByCrewIdAndStatusNot(Long crewId, String status);
}