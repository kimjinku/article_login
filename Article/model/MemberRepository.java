package Article.model;

import util.Util;

import java.util.ArrayList;

public class MemberRepository {
    private ArrayList<Member> members = new ArrayList<>();
    private int lastMemberId = 3;

    public MemberRepository() {
        Member m1 = new Member(1, "hong123", "1234", "홍길동", Util.getCurrentDate());
        Member m2 = new Member(2, "lee123", "1234", "이순신", Util.getCurrentDate());

        members.add(m1);
        members.add(m2);
    }

    public void insert(String loginId, String loginPw, String nickname) {
        Member member = new Member(lastMemberId, loginId, loginPw, nickname, Util.getCurrentDate());
        members.add(member);
        lastMemberId++;
    }

    public Member getMemberByLoginId(String loginId) {
        for (Member member : members) {
            if(member.getLoginId().equals(loginId)) {
                return member;
            }
        }

        return null;
    }

    public Member getMemberById(int id) {
        for(Member member : members) {
            if(member.getId() == id) {
                return member;
            }
        }

        return null;
    }
}