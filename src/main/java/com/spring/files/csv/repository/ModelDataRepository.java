package com.spring.files.csv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.files.csv.model.ModelData;

public interface ModelDataRepository extends JpaRepository<ModelData, Long> {

}
