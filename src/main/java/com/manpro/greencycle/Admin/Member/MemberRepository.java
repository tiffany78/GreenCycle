package com.manpro.greencycle.Admin.Member;

import java.util.List;

public interface MemberRepository {
    List<Member> findAll();
    List<Member> findWithFilter(String filter);
}
