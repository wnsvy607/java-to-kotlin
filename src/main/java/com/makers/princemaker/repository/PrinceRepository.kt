package com.makers.princemaker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.makers.princemaker.code.StatusCode;
import com.makers.princemaker.entity.Prince;

/**
 * @author Snow
 */
@Repository
public interface PrinceRepository extends JpaRepository<Prince, Long> {

	Optional<Prince> findByPrinceId(String princeId);

	List<Prince> findByStatusEquals(StatusCode status);
}
