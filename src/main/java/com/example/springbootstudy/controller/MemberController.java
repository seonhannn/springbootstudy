package com.example.springbootstudy.controller;

import com.example.springbootstudy.dto.MemberForm;
import com.example.springbootstudy.entity.Member;
import com.example.springbootstudy.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Slf4j
@Controller
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/signup")
    public String signUpPage() {
        return "/members/new";
    }

    @PostMapping("/join")
    public String join(MemberForm memberForm) {
        Member member = memberForm.toEntity();
        Member saved = memberRepository.save(member);
        return "redirect:/members/" + saved.getId();
    }

    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model) {
        Member member = memberRepository.findById(id).orElse(null);
        model.addAttribute("member", member);
        return "members/show";
    }

    @GetMapping("/members")
    public String index(Model model) {
        ArrayList<Member> memberList = memberRepository.findAll();
        model.addAttribute("memberList", memberList);
        return "members/index";
    }

    @GetMapping("/members/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        log.info("member edit");
        // id로 entity 가져오기
        Member memberEntity = memberRepository.findById(id).orElse(null);
        // model에 추가하기
        model.addAttribute("member", memberEntity);
        return "members/edit";
    }

    @PostMapping("/members/update")
    public String update(MemberForm form) {
        // form 데이터로 entity 가공하기
        Member memberEntity = form.toEntity();
        // DB에 해당 데이터가 존재하는지 확인하기
        Member target = memberRepository.findById(memberEntity.getId()).orElse(null);
        // 존재한다면, 업데이트하기
        if (target != null) {
            memberRepository.save(memberEntity);
        }
        return "redirect:/members/" + memberEntity.getId();
    }

    @GetMapping("/members/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        // DB에 해당 데이터가 존재하는지 조회하기
        Member target = memberRepository.findById(id).orElse(null);
        // 존재한다면 삭제하기
        if (target != null) {
            memberRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제되었습니다.");
        }
        return "redirect:/members";
    }
}
