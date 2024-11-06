package com.jovora.license;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class LicenseProcessor implements ApplicationRunner {

    public Object process(String license) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(license);

            RSAKey rsaKey = signedJWT.getHeader().getJWK().toRSAKey();

            JWSVerifier verifier = new RSASSAVerifier(rsaKey);
            if (!signedJWT.verify(verifier)) {
                return null;
            }

            // convert and validate TODO
            return signedJWT.getJWTClaimsSet();

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // TODO get license
        Object license = process("eyJhbGciOiJSUzI1NiIsImp3ayI6eyJrdHkiOiJSU0EiLCJlIjoiQVFBQiIsImtpZCI6ImxpY2Vuc2Uta2V5IiwibiI6InFpQk5zWE9aWXBlOFJIcEZJZmpIUlppT3c0UFFQX3lnTHlCdkhuOG93amZPTkVIUEp5aWNQRGNsZ0tHV1JPeExQZHZaTWFRRTJzQlJpQmFzRjQ2RV9MY0MxWHh0Zlg0djkzcFNSVms0Z1Q4QXBXX0dncHZrVU56b1YybHpMYVQycTNfY1AwYjlxR1l2WWlIV28wbVRvZWpPVmQ0ZE9YWVloSlR4eS1WcC0yOVYzbVhFTmo2dlRLSFZjbGdQQTJNZ0JlNHZGbUp1M1VFME9YV0V6ZUNCVm8xWlRMeHhkUmNhVTM4VDE0TWtWcnFldk5DMHVyaUdaOHZ0ck1XdDR5YWcxYkxaR0IxWlRQV2lFSTRGWk5yaVljbWhFU2dCd1Q2eEVkNkg4aVhTUWNSUnAzMy1vT0F4TTBqTXlIOW1PTXNYckUzd3dQRHpRYkZSQVctd0hlNzBCdyJ9fQ.eyJpc3MiOiJqb3ZvcmEiLCJzdWIiOiJMaWNlbnNlIGZvciBudWxsIn0.UiDOi256eu3_JRKHI2-oXuKCFfe7-_yx1elFvqIuf0BkCF1VkCYN7jv0_lTfrQjsC8-sjBLj-_UjE92i_fWllEL0FpINPfPysem5I7vAmmiCf_WRA_Q5GShI1jOhcFWJvAB31lYOLfUSrAMN0WyeA8l1cX6w3SyMLrBfMSGblyKx2QlWz_n5TlBTBn-2EOTr_gSeLLu6MwAjiK5i4c9Rv1DD32Exfn3eXXVXR_LnWRNKrdlkgd12SQ-28oBGuvn-_Pv1ld0gJv7P0nKKZBmFx6Hk9NU6cb6WQ0iyG5h5hHnbQkJlW8LFJtqZ0ly6Swj2lE965-bOzrs0tDjj1CtDCA");
        System.out.println(license.toString());

    }
}
