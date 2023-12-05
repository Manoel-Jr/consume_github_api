package br.com.consumerapigithub.infrastructure.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConstantesUtils {

    public static final String FORMAT_URL_ORG_REPO = "{org}/repos{?type,page,per_page,sort}";
    public static final String FORMAT_URL_COMMIT = "{/sha}";
    public static final String FORMAT_URL_ISSUE = "{query}{&page,per_page,sort,order}";
    public static final String AUTHORIZATION = "Authorization";
}
