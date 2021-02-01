package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService service;

    @GetMapping("members/new")
    public String createForm(final Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberFormPage";
    }

    @PostMapping("members/new")
    public String create(@Valid final MemberForm form, BindingResult result) {
        
        if (result.hasErrors()) {
            return "members/createMemberFormPage";
        }
        
        final var address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        final var member = new Member();
        member.setName(form.getName());
        member.setAddress(address);
        
        service.join(member);
        return "redirect:/";
    }

    @GetMapping("members")
    public String list(final Model model) {
        final List<Member> members = service.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
