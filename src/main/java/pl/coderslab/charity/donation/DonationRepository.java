package pl.coderslab.charity.donation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    @Query("select sum(d.quantity) from Donation d")
    Long numberOfPackages();
    @Query(value = "select count(*) from donations", nativeQuery = true)
    Long numberOfDonations();
    List<Donation> findAllByUserId(Long userId);
    List<Donation> findAllByInstitutionId(Long institutionId);
    void deleteAllByInstitutionId(Long institutionId);
}
