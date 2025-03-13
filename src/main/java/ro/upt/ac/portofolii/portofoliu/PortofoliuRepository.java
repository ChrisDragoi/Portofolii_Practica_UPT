package ro.upt.ac.portofolii.portofoliu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PortofoliuRepository extends JpaRepository<Portofoliu,Integer>
{
	Portofoliu findById(int id);
}
