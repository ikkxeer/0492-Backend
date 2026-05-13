package com.example.backend.repo;

import com.example.backend.domain.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackingRepository extends JpaRepository<Tracking, Integer> {
}
