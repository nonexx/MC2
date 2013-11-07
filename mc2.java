import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import org.multiclicker2.core.sites.BuxLikeSite;
import org.multiclicker2.core.sites.SimpleSite.Action;
import org.multiclicker2.core.sites.AbstractSite.Status;
import org.multiclicker2.net.Page;
import org.multiclicker2.net.post.MultipartPost;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.net.URL;
import java.util.LinkedHashMap;
import org.multiclicker2.net.post.Post;
import org.multiclicker2.utils.Hash;
public class Wizard extends BuxLikeSite {
    private static String name = null;
    protected List offset = new ArrayList();
    protected List cost = new ArrayList();
    protected List antic = new ArrayList();
    public static List captcha = new ArrayList();
    private static Integer cc,
    dd,
    ee,
    fin,
    inpput;
    private static Page temp;
    private static URL homepage;
    private static long advertTime;
    private static BigDecimal balancePerClickNormal;
    private static BigDecimal balancePerClickPremium;
    private static BigDecimal minbal;
    private static BigDecimal usminbal;
    private static BigDecimal avbal;
    private static String usernameKey;
    private static String passwordKey;
    private static String us,
    ps,
    CaptchaValue,
    URL_AS_STRING,
    nameses,
    check,
    va1,
    va2,
    va3,
    va4,
    vaaa,
    findd,
    captchaString,
    AnsweR,
    advp;
    private boolean inpputt;
    private static Pattern loginPagePattern;
    private static Pattern loginFormPattern;
    private static Pattern statisticsPagePattern;
    private static Pattern statisticsPremiumPattern;
    private static Pattern statisticsBalancePattern;
    private static Pattern statisticsTotalPaidPattern;
    private static Pattern statisticsClicksPattern;
    private static Pattern statisticsReferralsPattern;
    private static Pattern statisticsReferralsClicksPattern;
    private static Pattern surfPagePattern;
    private static Pattern surfSectionPattern;
    private static Pattern advertPagePattern;
    private static Map advertQueryPatterns;
    private static Pattern verifyPagePattern;
    private static Pattern verifyStringPattern;
    private static Page tmp,
    tmpl,
    tmpl1;
    private static void initStaticVariables() {
        if (name != null) {
            return;
        }
        name = "[NG] " + "globalpaid-bux";
        try {
            homepage = new URL("http://www.globalpaid-bux.com");
        } catch (MalformedURLException ex) {}
        advertTime = 30000;
        balancePerClickNormal = new BigDecimal("0.01");
        balancePerClickPremium = new BigDecimal("0.01");
        usernameKey = "username";
        passwordKey = "password";
        loginPagePattern = Pattern.compile("/(pages/login)", Pattern.DOTALL);
        statisticsPagePattern = Pattern.compile("href='/(pages/acc/accountsummary+)'", Pattern.DOTALL);
        statisticsPremiumPattern = Pattern.compile(" - premium<\\/a>", Pattern.DOTALL);
        statisticsBalancePattern = Pattern.compile(">Account Balance<\\D+([0-9.]+)", Pattern.DOTALL);
        statisticsTotalPaidPattern = Pattern.compile(">Total withdrawn<\\D+([0-9.]+)", Pattern.DOTALL);
        statisticsClicksPattern = Pattern.compile(">Your clicks<\\D+([0-9]+)", Pattern.DOTALL);
        statisticsReferralsPattern = Pattern.compile("Direct Referrals<\\D+([0-9]+)", Pattern.DOTALL);
        statisticsReferralsClicksPattern = Pattern.compile("Direct Referrals<\\D+\\d+\\D+([0-9]+)", Pattern.DOTALL);
        surfPagePattern = Pattern.compile("href=\"/([^\"]+)\"[^>]+>View Ads", Pattern.DOTALL);
        surfSectionPattern = Pattern.compile("(.+)", Pattern.DOTALL);
        advertPagePattern = Pattern.compile("id=clickads_wrapper2_[0-9]+ [^,]+,\"([^\"]+)", Pattern.DOTALL);
        verifyPagePattern = Pattern.compile("", Pattern.DOTALL);
        verifyStringPattern = Pattern.compile("(>Thank you<)", Pattern.DOTALL);
        loginFormPattern = Pattern.compile("href='/([^']+)'>Login", Pattern.DOTALL);
    }
    public Wizard() {
        initStaticVariables();
    }
    protected Pattern getLoginPagePattern() {
        return loginPagePattern;
    }
    protected Pattern getLoginFormPattern() {
        return loginFormPattern;
    }
    protected Pattern getStatisticsPagePattern() {
        return statisticsPagePattern;
    }
    protected Pattern getStatisticsPremiumPattern() {
        return statisticsPremiumPattern;
    }
    protected Pattern getStatisticsBalancePattern() {
        return statisticsBalancePattern;
    }
    protected Pattern getStatisticsTotalPattern() {
        return statisticsTotalPaidPattern;
    }
    protected Pattern getStatisticsClicksPattern() {
        return statisticsClicksPattern;
    }
    protected Pattern getStatisticsReferralsPattern() {
        return statisticsReferralsPattern;
    }
    protected Pattern getStatisticsReferralsClicksPattern() {
        return statisticsReferralsClicksPattern;
    }
    protected Pattern getSurfPagePattern() {
        return surfPagePattern;
    }
    protected Pattern getSurfSectionPattern() {
        return surfSectionPattern;
    }
    protected Pattern getAdvertPagePattern() {
        return advertPagePattern;
    }
    protected Map getAdvertQueryPatterns() {
        return advertQueryPatterns;
    }
    protected Pattern getVerifyPagePattern() {
        return verifyPagePattern;
    }
    protected Pattern getVerifyStringPattern() {
        return verifyStringPattern;
    }
    protected BigDecimal getBalancePerClick() {
        return isPremium() ? balancePerClickPremium : balancePerClickNormal;
    }
    public String getName() {
        return name;
    }
    public long getAdvertTime() {
        return advertTime;
    }
    public String getUsernameKey() {
        return usernameKey;
    }
    public String getPasswordKey() {
        return passwordKey;
    }
    public URL getHomepageURL() {
        return homepage;
    }
    protected Action getStatisticsPage() {
        try {
            URL_AS_STRING = "/pages/acc/accountsummary";
            lastPage = lastPage.open(URL_AS_STRING, (Post) null);
            if (lastPage == null) {
                return Action.connectionError;
            }
            Matcher DummyMatcher = Pattern.compile("(>Continue<)", Pattern.DOTALL).matcher(lastPage.getText());
            if (DummyMatcher.find()) {
                URL_AS_STRING = "/pages/acc/accountsummary";
                lastPage = lastPage.open(URL_AS_STRING, (Post) null);
            }
            Matcher m = statisticsClicksPattern.matcher(lastPage.getText());
            if (!m.find()) {
                DummyMatcher = Pattern.compile("var entriesperpage\\D+([0-9]+)", Pattern.DOTALL).matcher(lastPage.getText());
                DummyMatcher.find();
                MultipartPost post = new MultipartPost();
                post.addPart("getinbox", "1");
                post.addPart("params", "");
                post.addPart("entriesperpage", DummyMatcher.group(1));
                post.addPart("page", "1");
                post.addPart("sort", "2");
                post.addPart("ascdesc", "desc");
                post.addPart("search", "");
                post.addPart("searchfor", "");
                URL_AS_STRING = "/pages/acc/messagecenter";
                lastPage = lastPage.open(URL_AS_STRING, post);
                DummyMatcher = Pattern.compile("checkbox id\\D+([0-9]+)", Pattern.DOTALL).matcher(lastPage.getText());
                while (DummyMatcher.find()) {
                    post = new MultipartPost();
                    post.addPart("getmessage", "1");
                    post.addPart("ID", DummyMatcher.group(1));
                    post.addPart("menu", "inbox");
                    tmp = lastPage.open(post);
                }
                URL_AS_STRING = "/pages/acc/accountsummary";
                lastPage = lastPage.open(URL_AS_STRING, (Post) null);
            }
            Matcher st = statisticsBalancePattern.matcher(lastPage.getText());
            st.find();
            setBalance(new BigDecimal(st.group(1)));
            avbal = new BigDecimal(st.group(1));
            lastMatcher = statisticsTotalPaidPattern.matcher(lastPage.getText());
            lastMatcher.find();
            setTotal(new BigDecimal(lastMatcher.group(1)));
            lastMatcher = statisticsClicksPattern.matcher(lastPage.getText());
            lastMatcher.find();
            setClicks(Integer.parseInt(lastMatcher.group(1)));
            lastMatcher = statisticsReferralsPattern.matcher(lastPage.getText());
            lastMatcher.find();
            setReferrals(Integer.parseInt(lastMatcher.group(1)));
            lastMatcher = statisticsReferralsClicksPattern.matcher(lastPage.getText());
            lastMatcher.find();
            setReferralsClicks(Integer.parseInt(lastMatcher.group(1)));
            lastMatcher = getStatisticsPremiumPattern().matcher(lastPage.getText());
            setPremium(lastMatcher.find());
            return Action.next;
        } catch (MalformedURLException ex) {}
        return Action.modifiedScript;
    }
    private static ByteArrayOutputStream imgBytes;
    private String img2MD5(Image img) {
        try {
            BufferedImage image = (BufferedImage) img;
            imgBytes = new ByteArrayOutputStream();
            ImageIO.write(image, "png", imgBytes);
            return Hash.bytesToString(Hash.getMD5(imgBytes.toByteArray()));
        } catch (IOException ioe) {}
        return null;
    }
    protected Action getSurfPage() {
        try {
            offset.clear();
            cost.clear();
            antic.clear();
            antic.add("LOSE");
            antic.add("ATTENTION");
            antic.add("ANTY");
            antic.add("ANTI-CHEAT");
            antic.add("ANTICHEAT");
            antic.add("ANTI");
            antic.add("CHEAT");
            antic.add("DONOTCLICK");
            antic.add("SUSPENDED");
            antic.add("DONTCLICK");
            antic.add("NOTCLICK");
            URL_AS_STRING = "/pages/clickads";
            advp = homepage + "/pages/clickads";
            lastPage = lastPage.open(URL_AS_STRING, (Post) null);
            if (lastPage == null) {
                return Action.connectionError;
            }
            String dadv = " ",
            ad = lastPage.getText();
            Matcher m9 = Pattern.compile("disablead\\(([0-9]+)", Pattern.DOTALL).matcher(lastPage.getText());
            while (m9.find()) {
                dadv = "id=clickads_wrapper2_" + m9.group(1);
                ad = ad.replaceAll(dadv, "ad_   ");
            }
            Matcher a1 = Pattern.compile("(id=clickads_wrapper2_[0-9]+)", Pattern.DOTALL).matcher(ad);
            while (a1.find()) {
                String cheatstring = "";
                String cheatstring2 = "";
                String tempad = a1.group(1);
                String curads = tempad + ".*?clickads_title>([^<]+)";
                Matcher a2 = Pattern.compile(curads, Pattern.DOTALL).matcher(ad);
                a2.find();
                String a22 = a2.group(1);
                a22 = a22.replaceAll("_", "");
                a22 = a22.replaceAll(" ", "");
                curads = tempad + ".*?id=clickads_description_[0-9]+>([^<]+)";
                Matcher a3 = Pattern.compile(curads, Pattern.DOTALL).matcher(ad);
                a3.find();
                String a33 = a3.group(1);
                a33 = a33.replaceAll(" ", "");
                a33 = a33.replaceAll("_", "");
                String a44 = a22 + a33;
                Matcher aa2 = Pattern.compile("([\\w]+)", Pattern.DOTALL).matcher(a44);
                while (aa2.find()) {
                    cheatstring = cheatstring + aa2.group(1);
                }
                lastMatcher = Pattern.compile("([\\D]+)", Pattern.DOTALL).matcher(cheatstring);
                while (lastMatcher.find()) {
                    cheatstring2 = cheatstring2 + lastMatcher.group(1);
                }
                if (cheatstring2.equals("")) {
                    cheatstring2 = "CHEAT";
                }
                for (int i = 0; i < antic.size(); i++) {
                    String pat = "(" + antic.get(i) + ")";
                    lastMatcher = Pattern.compile(pat, Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(cheatstring2);
                    while (lastMatcher.find()) {
                        ad = ad.replaceAll(tempad, "ad_   ");
                        break;
                    }
                }
            }
            Matcher m3 = Pattern.compile("function openad[^?]+(pages/clickad[^']+)", Pattern.DOTALL).matcher(ad);
            m3.find();
            String aa = "",
            gg = "";
            Matcher p = getAdvertPagePattern().matcher(ad);
            if (p.find()) {
                do {
                    aa = p.group(1);
                    Matcher p1 = Pattern.compile(aa + ".*?info_payperclick\\D+([0-9.]+)", Pattern.DOTALL).matcher(ad);
                    if (p1.find()) {
                        cost.add(p1.group(1));
                        gg = m3.group(1) + p.group(1);
                        offset.add(gg);
                        printDebug(gg);
                    }
                } while (p.find());
            }
            currentAdvert = 0;
            adverts = offset.size();
            if (offset.size() == 0) {
                return Action.done;
            }
            return Action.next;
        } catch (MalformedURLException ex) {}
        return Action.modifiedScript;
    }
    protected Action getAdvertPage() {
        try {
            captchaString = null;
            AnsweR = null;
            inpputt = false;
            Image firstCatptchaImg = null,
            CurrentlyLoadedImg = null;
            cc = 0;
            String ap = "/" + offset.get(currentAdvert);
            lastPage = lastPage.open(ap, (Post) null, advp);
            if (lastPage == null) {
                return Action.connectionError;
            }
            Matcher m = Pattern.compile("(>Server Maintenance<)", Pattern.DOTALL).matcher(lastPage.getText());
            if (m.find()) {
                return Action.connectionError;
            }
            lastMatcher = Pattern.compile("var numbercounter \\D+([0-9]+)", Pattern.DOTALL).matcher(lastPage.getText());
            lastMatcher.find();
            advertTime = (Long.parseLong(lastMatcher.group(1))) * 1000;
            balancePerClickNormal = new BigDecimal(cost.get(currentAdvert).toString());
            currentAdvert++;
            Matcher hostl = Pattern.compile("id=ng_frm[^\\?]+src='(/pages/clickadsproc\\?[^']+)'", Pattern.DOTALL).matcher(lastPage.getText());
            if (hostl.find()) {
                String hl = hostl.group(1);
                tmpl = lastPage.open(hl, (Post) null);
            }
            lastMatcher = Pattern.compile("src='(/[^']+aptcha[^']+)", Pattern.DOTALL).matcher(lastPage.getText());
            lastMatcher.find();
            String ac = lastMatcher.group(1);
            temp = lastPage.open(ac, (Post) null);
            firstCatptchaImg = temp.getImage();
            if (firstCatptchaImg == null) {
                temp = lastPage.open(ac, (Post) null);
                firstCatptchaImg = temp.getImage();
            }
            if (firstCatptchaImg == null) {
                return Action.connectionError;
            }
            Matcher captchaMatcher = Pattern.compile("(docaptcha\\(4\\))", Pattern.DOTALL).matcher(lastPage.getText());
            if (captchaMatcher.find()) {
                String SecondCaptchaValue;
                int ca = 0,
                a = 0;
                CaptchaValue = (String) ngCaptchaHash.get(img2MD5(firstCatptchaImg));
                while (lastMatcher.find() && a == 0) {
                    ac = lastMatcher.group(1);
                    temp = lastPage.open(ac, (Post) null);
                    CurrentlyLoadedImg = temp.getImage();
                    if (CurrentlyLoadedImg == null) {
                        temp = lastPage.open(ac, (Post) null);
                        CurrentlyLoadedImg = temp.getImage();
                    }
                    if (CurrentlyLoadedImg == null) {
                        return Action.connectionError;
                    }
                    SecondCaptchaValue = (String) ngCaptchaHash.get(img2MD5(CurrentlyLoadedImg));
                    ca++;
                    if (CaptchaValue.equals(SecondCaptchaValue)) {
                        a = 1;
                    }
                }
                if (a == 0) {
                    cc = 256;
                }
                CaptchaValue = String.valueOf(ca);
                return Action.next;
            }
            if (!lastMatcher.find()) {
                CaptchaValue = (String) ngCaptchaHash.get(img2MD5(firstCatptchaImg));
                return Action.next;
            }
            ac = lastMatcher.group(1);
            temp = lastPage.open(ac, (Post) null);
            CurrentlyLoadedImg = temp.getImage();
            if (CurrentlyLoadedImg == null) {
                temp = lastPage.open(ac, (Post) null);
                CurrentlyLoadedImg = temp.getImage();
            }
            if (CurrentlyLoadedImg == null) {
                return Action.connectionError;
            }
            if (CurrentlyLoadedImg.getWidth() > 40 && firstCatptchaImg.getWidth() < 40) {
                if (currentAdvert >= offset.size()) {
                    return Action.done;
                }
                return Action.next;
            }
            if (CurrentlyLoadedImg.getWidth() < 40 && firstCatptchaImg.getWidth() > 40) {
                if (currentAdvert >= offset.size()) {
                    return Action.done;
                }
                return Action.next;
            }
            if (CurrentlyLoadedImg.getWidth() > 40) {
                CaptchaValue = (String) ngCaptchaHash.get(img2MD5(firstCatptchaImg));
                if (CaptchaValue == null) {
                    CaptchaValue = showCaptchaDialog(firstCatptchaImg);
                    printDebug(img2MD5(firstCatptchaImg) + "|" + CaptchaValue);
                }
                dd = Integer.parseInt(CaptchaValue);
                CaptchaValue = (String) ngCaptchaHash.get(img2MD5(CurrentlyLoadedImg));
                if (CaptchaValue == null) {
                    CaptchaValue = showCaptchaDialog(firstCatptchaImg);
                    printDebug(img2MD5(firstCatptchaImg) + "|" + CaptchaValue);
                }
                ee = Integer.parseInt(CaptchaValue);
                cc = dd + ee;
                return Action.next;
            }
            CaptchaValue = (String) ngCaptchaHash.get(img2MD5(firstCatptchaImg));
            if (CaptchaValue == null) {
                CaptchaValue = showCaptchaDialog(firstCatptchaImg);
                printDebug(img2MD5(firstCatptchaImg) + "|" + CaptchaValue);
            }
            dd = Integer.parseInt(CaptchaValue);
            CaptchaValue = (String) ngCaptchaHash.get(img2MD5(CurrentlyLoadedImg));
            if (CaptchaValue == null) {
                CaptchaValue = showCaptchaDialog(firstCatptchaImg);
                printDebug(img2MD5(firstCatptchaImg) + "|" + CaptchaValue);
            }
            ee = Integer.parseInt(CaptchaValue);
            cc = dd + ee;
            CaptchaValue = cc.toString();
            return Action.next;
        } catch (MalformedURLException ex) {}
        return Action.modifiedScript;
    }
    protected Action getVerifyPage() {
        try {
            if (cc == 256) {
                currentAdvert--;
                return Action.next;
            }
            Matcher mm = Pattern.compile("docaptcha=([^&]+)", Pattern.DOTALL).matcher(lastPage.getText());
            mm.find();
            va1 = mm.group(1);
            Matcher mm1 = Pattern.compile("rnd=([^&]+)", Pattern.DOTALL).matcher(lastPage.getText());
            mm1.find();
            va2 = mm1.group(1);
            Matcher mm2 = Pattern.compile("rnd=[^&]+&h=([^&]+)", Pattern.DOTALL).matcher(lastPage.getText());
            mm2.find();
            va3 = mm2.group(1);
            Matcher mm3 = Pattern.compile("rnd=[^&]+&h=[^&]+&token=([^&]+)", Pattern.DOTALL).matcher(lastPage.getText());
            mm3.find();
            va4 = mm3.group(1);
            Matcher mv = Pattern.compile("getaddon\\('(/[^']+)", Pattern.DOTALL).matcher(lastPage.getText());
            mv.find();
            vaaa = mv.group(1);
            MultipartPost post = new MultipartPost();
            post.addPart("docaptcha", va1);
            post.addPart("rnd", va2);
            post.addPart("h", va3);
            post.addPart("token", va4);
            post.addPart("c", CaptchaValue);
            String vr = vaaa;
            lastPage = lastPage.open(vr, post);
            if (lastPage == null) {
                return Action.connectionError;
            }
            Matcher m = Pattern.compile("(You can click this advertisement only once per day)", Pattern.DOTALL).matcher(lastPage.getText());
            if (m.find()) {
                waitTime(Status.waiting, 61000);
                if (currentAdvert >= offset.size()) {
                    return Action.done;
                }
                return Action.next;
            }
            m = Pattern.compile("(>You have clicked the maximum number of ads for today)", Pattern.DOTALL).matcher(lastPage.getText());
            if (m.find()) {
                return Action.done;
            }
            lastMatcher = Pattern.compile("(>Thank you<)", Pattern.DOTALL).matcher(lastPage.getText());
            if (!lastMatcher.find()) {
                currentAdvert--;
                return Action.next;
            }
            if (getVerifyStringPattern().matcher(lastPage.getText()).find()) {
                addNewClicks(1);
                addClicks(1);
                addNewBalance(getBalancePerClick());
                addBalance(getBalancePerClick());
            }
            if (currentAdvert >= offset.size()) {
                return Action.done;
            }
            return Action.next;
        } catch (MalformedURLException ex) {}
        return Action.modifiedScript;
    }
    protected Action getLoginPage() {
        getDBxCaptcha();
        try {
            Set set = getLoginDetails().entrySet();
            Iterator it = set.iterator();
            it.hasNext();
            Entry entry = (Entry) it.next();
            String value = (String) entry.getValue();
            entry = (Entry) it.next();
            String pass = (String) entry.getValue();
            lastPage = lastPage.open(loginPagePattern, (Post) null);
            if (lastPage == null) {
                return Action.connectionError;
            }
            Matcher m = Pattern.compile(">Log off<", Pattern.DOTALL).matcher(lastPage.getText());
            if (m.find()) {
                return Action.next;
            }
            Map map = getLoginDetails();
            String serialkey = (String) map.get("Serial Key");
            String fixedname = name.replaceAll("\\[NG\\] ", "");
            if (checkKey(fixedname, value, serialkey) == false) {
                return Action.badSerialKey;
            }
            String aa1 = " ",
            aa2 = " ",
            aa3 = " ";
            String ps1 = null;
            Matcher m2 = Pattern.compile("Username<[^;]+[^>]+type=text name=([^ ]+) id", Pattern.DOTALL).matcher(lastPage.getText());
            if (m2.find()) {
                us = m2.group(1);
            }
            m2 = Pattern.compile("osk_field=this;[^>]+name=([0-9]+)[^;]+Username", Pattern.DOTALL).matcher(lastPage.getText());
            if (m2.find()) {
                aa1 = m2.group(1);
            }
            lastMatcher = Pattern.compile("Password[^>]+>[^>]+>[^>]+type=password name=([0-9a-z]+)", Pattern.DOTALL).matcher(lastPage.getText());
            if (lastMatcher.find()) {
                ps = lastMatcher.group(1);
            }
            if (lastMatcher.find()) {
                ps1 = lastMatcher.group(1);
            }
            lastMatcher = Pattern.compile("optional[^;]+;[^;]+;[^;]+[^>]+name=([0-9]+)", Pattern.DOTALL).matcher(lastPage.getText());
            if (lastMatcher.find()) {
                aa2 = lastMatcher.group(1);
            }
            lastMatcher = Pattern.compile("optional[^;]+;[^;]+;[^;]+;[^;]+[^>]+name=([0-9]+)", Pattern.DOTALL).matcher(lastPage.getText());
            if (lastMatcher.find()) {
                aa3 = lastMatcher.group(1);
            }
            Page img;
            m = Pattern.compile("(/pages/captcha\\?[^\"]+)", Pattern.DOTALL).matcher(lastPage.getText());
            if (m.find()) {
                String dd = m.group(1);
                img = lastPage.open(m.group(1), (Post) null);
                Image im = img.getImage();
                CaptchaValue = showCaptchaDialog(im);
                CaptchaValue = CaptchaValue.toUpperCase();
            }
            MultipartPost post = new MultipartPost();
            post.addPart(aa1, "");
            post.addPart("dosubmit", "1");
            post.addPart(us, value);
            post.addPart(ps, pass);
            post.addPart(ps1, "");
            post.addPart(aa2, "");
            post.addPart(aa3, "");
            post.addPart("rememberusername", "true");
            post.addPart("autologin", "true");
            post.addPart("captcha", CaptchaValue);
            URL_AS_STRING = "pages/login";
            lastPage = lastPage.open(URL_AS_STRING, post);
            if (lastPage == null) {
                return Action.connectionError;
            }
            Matcher l = Pattern.compile("(Access granted)", Pattern.DOTALL).matcher(lastPage.getText());
            if (!l.find()) {
                return Action.badLoginData;
            }
            return Action.next;
        } catch (MalformedURLException ex) {}
        return Action.modifiedScript;
    }
    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, type, bos);
            byte[]imageBytes = bos.toByteArray();
            imageString = Hash.bytesToString(imageBytes);
            imageString = Hash.getMD5(imageString);
            bos.close();
        } catch (IOException e) {}
        return imageString;
    }
    private boolean checkKey(String sitename, String username, String key) {
        String bkey = sitename + "[click]" + username;
        String ckey = Hash.getMD5(bkey);
        return ckey.equals(key);
    }
    private LinkedHashMap ngCaptchaHash = new LinkedHashMap();
    private void getDBxCaptcha() {
        try {
            Page CaptchaPage = lastPage.open("http://dl.dropboxusercontent.com/s/6h1kmb5v22hdtk8/captchafucker.txt", (Post) null);
            String[]fullSet = CaptchaPage.getText().split("\r\n");
            for (int i = 0; i < fullSet.length; i++) {
                ngCaptchaHash.put(fullSet[i].split("\\|")[0], fullSet[i].split("\\|")[1]);
            }
            printDebug(ngCaptchaHash.size());
        } catch (MalformedURLException ex) {}
    }
}
