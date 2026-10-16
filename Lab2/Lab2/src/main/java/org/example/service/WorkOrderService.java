package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.WorkOrderDto;
import org.example.entity.CityReport;
import org.example.entity.WorkCrew;
import org.example.entity.WorkOrder;
import org.example.repository.CityReportRepository;
import org.example.repository.WorkCrewRepository;
import org.example.repository.WorkOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkOrderService {

    private final WorkOrderRepository orderRepository;
    private final WorkCrewRepository crewRepository;
    private final CityReportRepository reportRepository;

    public List<WorkOrder> findAll() {
        return orderRepository.findAll();
    }

    public WorkOrder findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkOrder not found with id: " + id));
    }

    public WorkOrder create(WorkOrderDto dto) {
        WorkCrew crew = crewRepository.findById(dto.getCrewId())
                .orElseThrow(() -> new RuntimeException("WorkCrew not found"));
        CityReport report = reportRepository.findById(dto.getReportId())
                .orElseThrow(() -> new RuntimeException("CityReport not found"));

        String objectType = report.getCityObject() != null
                ? report.getCityObject().getType()
                : null;
        if (objectType == null
                || crew.getSupportedObjectTypes() == null
                || !crew.getSupportedObjectTypes().contains(objectType)) {
            throw new RuntimeException("Crew does not support object type: " + objectType);
        }

        long activeCount = orderRepository.countByCrewIdAndStatusNot(crew.getId(), "COMPLETED");
        if (activeCount >= crew.getActiveTasksLimit()) {
            throw new RuntimeException("Crew has reached the active tasks limit");
        }

        WorkOrder order = new WorkOrder();
        order.setCrew(crew);
        order.setReport(report);
        order.setDeadline(dto.getDeadline());
        order.setStatus(dto.getStatus() != null ? dto.getStatus() : "ASSIGNED");
        order.setResultDescription(dto.getResultDescription());

        report.setStatus("IN_PROGRESS");
        reportRepository.save(report);

        return orderRepository.save(order);
    }

    public WorkOrder update(Long id, WorkOrderDto dto) {
        WorkOrder order = findById(id);

        if (dto.getDeadline() != null) order.setDeadline(dto.getDeadline());
        if (dto.getStatus() != null) order.setStatus(dto.getStatus());
        if (dto.getResultDescription() != null) order.setResultDescription(dto.getResultDescription());

        return orderRepository.save(order);
    }

    public void delete(Long id) {
        findById(id);
        orderRepository.deleteById(id);
    }
}