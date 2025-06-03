package ro.upt.ac.portofolii.portofoliu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortofoliuRepository extends JpaRepository<Portofoliu,Integer>
{
	Portofoliu findById(int id);
}
