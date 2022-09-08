package pl.coderslab.charity.institution;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.charity.category.CategoryRepository;
import pl.coderslab.charity.donation.DonationRepository;
import pl.coderslab.charity.user.CurrentUser;
import pl.coderslab.charity.user.RoleRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
public class InstitutionController {
    private final InstitutionRepository institutionRepository;
    private final DonationRepository donationRepository;
    private final CategoryRepository categoryRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public InstitutionController(InstitutionRepository institutionRepository,
                                 DonationRepository donationRepository,
                                 CategoryRepository categoryRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.institutionRepository = institutionRepository;
        this.donationRepository = donationRepository;
        this.categoryRepository = categoryRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/institutions")
    public String list(Model model) {
        model.addAttribute("institutions", institutionRepository.findAll());
        return "institutions";
    }
    @GetMapping("/admin/add/institution")
    public String add(Model model) {
        model.addAttribute("institution", new Institution());
        return "add-institution";
    }
    @PostMapping("/admin/add/institution")
    public String save(@Valid Institution institution, BindingResult result, Model model) {
        if (result.hasErrors()){
            return "add-institution";
        }
        institutionRepository.save(institution);
        return "redirect:/institutions";
    }
    @GetMapping("/admin/edit/institution/{id}")
    public String edit(Model model, @PathVariable long id) {
        model.addAttribute("institution", institutionRepository.findById(id).orElseThrow(EntityNotFoundException::new));
        return "edit-institution";
    }
    @PostMapping("/admin/update/institution")
    public String update(@Valid Institution institution, BindingResult result, Model model) {
        if (result.hasErrors()){
            return "edit-institution";
        }
        institutionRepository.save(institution);
        return "redirect:/institutions";
    }
    @GetMapping("/admin/deleteConfirm/institution/{id}")
    public String confirm(Model model, @PathVariable long id) {
        Institution institution = institutionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("institutionId", institution.getId());
        return "confirm-delete-institution";
    }
    @GetMapping("/admin/delete/institution/{id}")
    @Transactional
    public String delete(@PathVariable long id, @RequestParam String password, @AuthenticationPrincipal CurrentUser user) {
        Institution institution = institutionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (passwordEncoder.matches(password, user.getUser().getPassword())){
            donationRepository.deleteAllByInstitutionId(id);
            institutionRepository.delete(institution);
            return "redirect:/institutions";
        }
        return "redirect:/error";
    }
}
