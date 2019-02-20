
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Enrolment;

public interface EnrolmentRepository extends JpaRepository<Enrolment, Integer> {

}
