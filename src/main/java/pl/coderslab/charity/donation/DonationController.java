package pl.coderslab.charity.donation;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.charity.category.CategoryRepository;
import pl.coderslab.charity.emailSender.EmailService;
import pl.coderslab.charity.institution.InstitutionRepository;
import pl.coderslab.charity.user.CurrentUser;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.time.LocalDate;

@Controller
public class DonationController {
    private final DonationRepository donationRepository;
    private final CategoryRepository categoryRepository;
    private final InstitutionRepository institutionRepository;
    private final EmailService emailService;

    public DonationController(DonationRepository donationRepository, CategoryRepository categoryRepository, InstitutionRepository institutionRepository, EmailService emailService) {
        this.donationRepository = donationRepository;
        this.categoryRepository = categoryRepository;
        this.institutionRepository = institutionRepository;
        this.emailService = emailService;
    }
    @GetMapping("/donation/form")
    public String add(Model model) {
        model.addAttribute("donation", new Donation());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("institutions", institutionRepository.findAll());
        return "form";
    }
    @PostMapping("/donation/form")
    public String save(@Valid Donation donation, BindingResult result, Model model, @AuthenticationPrincipal CurrentUser user) {
        if (result.hasErrors()){
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("institutions", institutionRepository.findAll());
            return "form";
        }
        if (user != null) {
            donation.setUser(user.getUser());
            emailService.sendSimpleMessage(user.getUser().getEmail(),"przekazanie daru","dziekujemy za przekazanie daru dla fundacji " + donation.getInstitution());
        }
        donationRepository.save(donation);

        return "donation-confirm";
    }
    @GetMapping("/user/donations")
    public String userAuthentication(Model model, @AuthenticationPrincipal CurrentUser user) {
        model.addAttribute("donations", donationRepository.findAllByUserId(user.getUser().getId()));
        return "user-donations";
    }
    @GetMapping("/user/donation-status-success/{id}")
    public String donationStatusSuccess(@PathVariable Long id, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        Donation donation = donationRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        donation.setStatus("odebrano");
        donation.setPickUpSuccessDate(LocalDate.now());
        donationRepository.save(donation);
        return "redirect:/user/donations";
    }
    @GetMapping("/user/donation-status-in-progress/{id}")
    public String donationStatusInProgress(@PathVariable Long id, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        Donation donation = donationRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        donation.setStatus("nie odebrano");
        donation.setPickUpSuccessDate(null);
        donationRepository.save(donation);
        return "redirect:/user/donations";
    }


}
