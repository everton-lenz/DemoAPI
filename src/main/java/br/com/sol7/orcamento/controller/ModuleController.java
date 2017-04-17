package br.com.sol7.orcamento.controller;

import br.com.sol7.orcamento.model.Module;
import br.com.sol7.orcamento.service.BaseService;
import br.com.sol7.orcamento.service.ModuleService;
import br.com.sol7.orcamento.util.MessageUtil;
import org.lindbergframework.spring.scope.AccessScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Controller
@AccessScoped
public class ModuleController extends BaseController<Module, Integer> implements Serializable {

    @Autowired
    private ModuleService moduleService;

    private List<Module> moduleList = new ArrayList<Module>();

    private List<String> listIcons = new ArrayList<String>();

    private List<Module> modules = new ArrayList<Module>();

    private List<Module> filteredModule;

    public ModuleController() {
        super(Module.class);
    }

    @Override
    public String save() {
        try{
            moduleService.save(getEntity());
            getMessageUtil().sendMessageToUser(MessageUtil.MessageUtilType.INFO, "global.info", "global.insert.sucess", null, "Perfil");
            init();
            return getListPath();
        }catch (Exception e){
            getMessageUtil().sendMessageToUser(MessageUtil.MessageUtilType.ERROR, "global.error", "global.insert.error", null, "Perfil");
            throw new RuntimeException();
        }
    }

    public List<Module> listAllModule(){
        return moduleService.findAll();
    }

    public void loadAllModuleModules(){
        modules = moduleService.getRepository().findAllModule();
    }

    public String prepareNewEntity(){
        setEntity(new Module());
        return getFormPath();
    }

    @PostConstruct
    public void init(){
        moduleList = moduleService.findAll();
        listIcons = montarIcon();
        loadAllModuleModules();
    }

    public void refreshView(){
        init();
        filteredModule = null;
    }

    @Override
    public void reset() throws Exception {
        super.reset();
    }

    @Override
    public void setEntity(Module entity) {
        filteredModule= null;
        super.setEntity(entity);
    }

    @Override
    public String getFormPath(){
        return "/view/module/formModule.xhtml?faces-redirect=true";
    }

    @Override
    public String getListPath() {
        return "/view/module/listModule.xhtml?faces-redirect=true";
    }

    public String back() {
        init();
        return "/view/module/listModule.xhtml?faces-redirect=true";
    }

    @Override
    public BaseService<Module, Integer> getBaseService() {
        return moduleService;
    }

    @Override
    public void delete() {
        super.delete();
        init();
    }

    public void setaIcon(String icon){
        getEntity().setIcon(icon);
    }

    public List<String> montarIcon(){
        List<String> list = new ArrayList<String>();
        list.add("fa fa-adjust");
        list.add("fa fa-adn");
        list.add("fa fa-align-center");
        list.add("fa fa-align-justify");
        list.add("fa fa-align-left");
        list.add("fa fa-align-right");
        list.add("fa fa-ambulance");
        list.add("fa fa-anchor");
        list.add("fa fa-android");
        list.add("fa fa-angellist");
        list.add("fa fa-angle-double-down");
        list.add("fa fa-angle-double-left");
        list.add("fa fa-angle-double-right");
        list.add("fa fa-angle-double-up");
        list.add("fa fa-angle-down");
        list.add("fa fa-angle-left");
        list.add("fa fa-angle-right");
        list.add("fa fa-angle-up");
        list.add("fa fa-apple");
        list.add("fa fa-archive");
        list.add("fa fa-area-chart");
        list.add("fa fa-arrow-circle-down");
        list.add("fa fa-arrow-circle-left");
        list.add("fa fa-arrow-circle-o-down");
        list.add("fa fa-arrow-circle-o-left");
        list.add("fa fa-arrow-circle-o-right");
        list.add("fa fa-arrow-circle-o-up");
        list.add("fa fa-arrow-circle-right");
        list.add("fa fa-arrow-circle-up");
        list.add("fa fa-arrow-down");
        list.add("fa fa-arrow-left");
        list.add("fa fa-arrow-right");
        list.add("fa fa-arrow-up");
        list.add("fa fa-arrows");
        list.add("fa fa-arrows-alt");
        list.add("fa fa-arrows-h");
        list.add("fa fa-arrows-v");
        list.add("fa fa-asterisk");
        list.add("fa fa-at");
        list.add("fa fa-automobile");
        list.add("fa fa-backward");
        list.add("fa fa-ban");
        list.add("fa fa-bank");
        list.add("fa fa-bar-chart");
        list.add("fa fa-bar-chart-o");
        list.add("fa fa-barcode");
        list.add("fa fa-bars");
        list.add("fa fa-bed");
        list.add("fa fa-beer");
        list.add("fa fa-behance");
        list.add("fa fa-behance-square");
        list.add("fa fa-bell");
        list.add("fa fa-bell-o");
        list.add("fa fa-bell-slash");
        list.add("fa fa-bell-slash-o");
        list.add("fa fa-bicycle");
        list.add("fa fa-binoculars");
        list.add("fa fa-birthday-cake");
        list.add("fa fa-bitbucket");
        list.add("fa fa-bitbucket-square");
        list.add("fa fa-bitcoin");
        list.add("fa fa-bold");
        list.add("fa fa-bolt");
        list.add("fa fa-bomb");
        list.add("fa fa-book");
        list.add("fa fa-bookmark");
        list.add("fa fa-bookmark-o");
        list.add("fa fa-briefcase");
        list.add("fa fa-btc");
        list.add("fa fa-bug");
        list.add("fa fa-building");
        list.add("fa fa-building-o");
        list.add("fa fa-bullhorn");
        list.add("fa fa-bullseye");
        list.add("fa fa-bus");
        list.add("fa fa-buysellads");
        list.add("fa fa-cab");
        list.add("fa fa-calculator");
        list.add("fa fa-calendar");
        list.add("fa fa-calendar-o");
        list.add("fa fa-camera");
        list.add("fa fa-camera-retro");
        list.add("fa fa-car");
        list.add("fa fa-caret-down");
        list.add("fa fa-caret-left");
        list.add("fa fa-caret-right");
        list.add("fa fa-caret-square-o-down");
        list.add("fa fa-caret-square-o-left");
        list.add("fa fa-caret-square-o-right");
        list.add("fa fa-caret-square-o-up");
        list.add("fa fa-caret-up");
        list.add("fa fa-cart-arrow-down");
        list.add("fa fa-cart-plus");
        list.add("fa fa-cc");
        list.add("fa fa-cc-amex");
        list.add("fa fa-cc-discover");
        list.add("fa fa-cc-mastercard");
        list.add("fa fa-cc-paypal");
        list.add("fa fa-cc-stripe");
        list.add("fa fa-cc-visa");
        list.add("fa fa-certificate");
        list.add("fa fa-chain");
        list.add("fa fa-chain-broken");
        list.add("fa fa-check");
        list.add("fa fa-check-circle");
        list.add("fa fa-check-circle-o");
        list.add("fa fa-check-square");
        list.add("fa fa-check-square-o");
        list.add("fa fa-chevron-circle-down");
        list.add("fa fa-chevron-circle-left");
        list.add("fa fa-chevron-circle-right");
        list.add("fa fa-chevron-circle-up");
        list.add("fa fa-chevron-down");
        list.add("fa fa-chevron-left");
        list.add("fa fa-chevron-right");
        list.add("fa fa-chevron-up");
        list.add("fa fa-child");
        list.add("fa fa-circle");
        list.add("fa fa-circle-o");
        list.add("fa fa-circle-o-notch");
        list.add("fa fa-circle-thin");
        list.add("fa fa-clipboard");
        list.add("fa fa-clock-o");
        list.add("fa fa-close");
        list.add("fa fa-cloud");
        list.add("fa fa-cloud-download");
        list.add("fa fa-cloud-upload");
        list.add("fa fa-cny");
        list.add("fa fa-code");
        list.add("fa fa-code-fork");
        list.add("fa fa-codepen");
        list.add("fa fa-coffee");
        list.add("fa fa-cog");
        list.add("fa fa-cogs");
        list.add("fa fa-columns");
        list.add("fa fa-comment");
        list.add("fa fa-comment-o");
        list.add("fa fa-comments");
        list.add("fa fa-comments-o");
        list.add("fa fa-compass");
        list.add("fa fa-compress");
        list.add("fa fa-connectdevelop");
        list.add("fa fa-copy");
        list.add("fa fa-copyright");
        list.add("fa fa-credit-card");
        list.add("fa fa-crop");
        list.add("fa fa-crosshairs");
        list.add("fa fa-css3");
        list.add("fa fa-cube");
        list.add("fa fa-cubes");
        list.add("fa fa-cut");
        list.add("fa fa-cutlery");
        list.add("fa fa-dashboard");
        list.add("fa fa-dashcube");
        list.add("fa fa-database");
        list.add("fa fa-dedent");
        list.add("fa fa-delicious");
        list.add("fa fa-desktop");
        list.add("fa fa-deviantart");
        list.add("fa fa-diamond");
        list.add("fa fa-digg");
        list.add("fa fa-dollar");
        list.add("fa fa-dot-circle-o");
        list.add("fa fa-download");
        list.add("fa fa-dribbble");
        list.add("fa fa-dropbox");
        list.add("fa fa-drupal");
        list.add("fa fa-edit");
        list.add("fa fa-eject");
        list.add("fa fa-ellipsis-h");
        list.add("fa fa-ellipsis-v");
        list.add("fa fa-empire");
        list.add("fa fa-envelope");
        list.add("fa fa-envelope-o");
        list.add("fa fa-envelope-square");
        list.add("fa fa-eraser");
        list.add("fa fa-eur");
        list.add("fa fa-euro");
        list.add("fa fa-exchange");
        list.add("fa fa-exclamation");
        list.add("fa fa-exclamation-circle");
        list.add("fa fa-exclamation-triangle");
        list.add("fa fa-expand");
        list.add("fa fa-external-link");
        list.add("fa fa-external-link-square");
        list.add("fa fa-eye");
        list.add("fa fa-eye-slash");
        list.add("fa fa-eyedropper");
        list.add("fa fa-facebook");
        list.add("fa fa-facebook-f");
        list.add("fa fa-facebook-official");
        list.add("fa fa-facebook-square");
        list.add("fa fa-fast-backward");
        list.add("fa fa-fast-forward");
        list.add("fa fa-fax");
        list.add("fa fa-female");
        list.add("fa fa-fighter-jet");
        list.add("fa fa-file");
        list.add("fa fa-file-archive-o");
        list.add("fa fa-file-audio-o");
        list.add("fa fa-file-code-o");
        list.add("fa fa-file-excel-o");
        list.add("fa fa-file-image-o");
        list.add("fa fa-file-movie-o");
        list.add("fa fa-file-o");
        list.add("fa fa-file-pdf-o");
        list.add("fa fa-file-photo-o");
        list.add("fa fa-file-picture-o");
        list.add("fa fa-file-powerpoint-o");
        list.add("fa fa-file-sound-o");
        list.add("fa fa-file-text");
        list.add("fa fa-file-text-o");
        list.add("fa fa-file-video-o");
        list.add("fa fa-file-word-o");
        list.add("fa fa-file-zip-o");
        list.add("fa fa-files-o");
        list.add("fa fa-film");
        list.add("fa fa-filter");
        list.add("fa fa-fire");
        list.add("fa fa-fire-extinguisher");
        list.add("fa fa-flag");
        list.add("fa fa-flag-checkered");
        list.add("fa fa-flag-o");
        list.add("fa fa-flash");
        list.add("fa fa-flask");
        list.add("fa fa-flickr");
        list.add("fa fa-floppy-o");
        list.add("fa fa-folder");
        list.add("fa fa-folder-o");
        list.add("fa fa-folder-open");
        list.add("fa fa-folder-open-o");
        list.add("fa fa-font");
        list.add("fa fa-forumbee");
        list.add("fa fa-forward");
        list.add("fa fa-foursquare");
        list.add("fa fa-frown-o");
        list.add("fa fa-futbol-o");
        list.add("fa fa-gamepad");
        list.add("fa fa-gavel");
        list.add("fa fa-gbp");
        list.add("fa fa-ge");
        list.add("fa fa-gear");
        list.add("fa fa-gears");
        list.add("fa fa-genderless");
        list.add("fa fa-gift");
        list.add("fa fa-git");
        list.add("fa fa-git-square");
        list.add("fa fa-github");
        list.add("fa fa-github-alt");
        list.add("fa fa-github-square");
        list.add("fa fa-gittip");
        list.add("fa fa-glass");
        list.add("fa fa-globe");
        list.add("fa fa-google");
        list.add("fa fa-google-plus");
        list.add("fa fa-google-plus-square");
        list.add("fa fa-google-wallet");
        list.add("fa fa-graduation-cap");
        list.add("fa fa-gratipay");
        list.add("fa fa-group");
        list.add("fa fa-h-square");
        list.add("fa fa-hacker-news");
        list.add("fa fa-hand-o-down");
        list.add("fa fa-hand-o-left");
        list.add("fa fa-hand-o-right");
        list.add("fa fa-hand-o-up");
        list.add("fa fa-hdd-o");
        list.add("fa fa-header");
        list.add("fa fa-headphones");
        list.add("fa fa-heart");
        list.add("fa fa-heart-o");
        list.add("fa fa-heartbeat");
        list.add("fa fa-history");
        list.add("fa fa-home");
        list.add("fa fa-hospital-o");
        list.add("fa fa-hotel");
        list.add("fa fa-html5");
        list.add("fa fa-ils");
        list.add("fa fa-image");
        list.add("fa fa-inbox");
        list.add("fa fa-indent");
        list.add("fa fa-info");
        list.add("fa fa-info-circle");
        list.add("fa fa-inr");
        list.add("fa fa-instagram");
        list.add("fa fa-institution");
        list.add("fa fa-ioxhost");
        list.add("fa fa-italic");
        list.add("fa fa-joomla");
        list.add("fa fa-jpy");
        list.add("fa fa-jsfiddle");
        list.add("fa fa-key");
        list.add("fa fa-keyboard-o");
        list.add("fa fa-krw");
        list.add("fa fa-language");
        list.add("fa fa-laptop");
        list.add("fa fa-lastfm");
        list.add("fa fa-lastfm-square");
        list.add("fa fa-leaf");
        list.add("fa fa-leanpub");
        list.add("fa fa-legal");
        list.add("fa fa-lemon-o");
        list.add("fa fa-level-down");
        list.add("fa fa-level-up");
        list.add("fa fa-life-bouy");
        list.add("fa fa-life-buoy");
        list.add("fa fa-life-ring");
        list.add("fa fa-life-saver");
        list.add("fa fa-lightbulb-o");
        list.add("fa fa-line-chart");
        list.add("fa fa-link");
        list.add("fa fa-linkedin");
        list.add("fa fa-linkedin-square");
        list.add("fa fa-linux");
        list.add("fa fa-list");
        list.add("fa fa-list-alt");
        list.add("fa fa-list-ol");
        list.add("fa fa-list-ul");
        list.add("fa fa-location-arrow");
        list.add("fa fa-lock");
        list.add("fa fa-long-arrow-down");
        list.add("fa fa-long-arrow-left");
        list.add("fa fa-long-arrow-right");
        list.add("fa fa-long-arrow-up");
        list.add("fa fa-magic");
        list.add("fa fa-magnet");
        list.add("fa fa-mail-forward");
        list.add("fa fa-mail-reply");
        list.add("fa fa-mail-reply-all");
        list.add("fa fa-male");
        list.add("fa fa-map-marker");
        list.add("fa fa-mars");
        list.add("fa fa-mars-double");
        list.add("fa fa-mars-stroke");
        list.add("fa fa-mars-stroke-h");
        list.add("fa fa-mars-stroke-v");
        list.add("fa fa-maxcdn");
        list.add("fa fa-meanpath");
        list.add("fa fa-medium");
        list.add("fa fa-medkit");
        list.add("fa fa-meh-o");
        list.add("fa fa-mercury");
        list.add("fa fa-microphone");
        list.add("fa fa-microphone-slash");
        list.add("fa fa-minus");
        list.add("fa fa-minus-circle");
        list.add("fa fa-minus-square");
        list.add("fa fa-minus-square-o");
        list.add("fa fa-mobile");
        list.add("fa fa-mobile-phone");
        list.add("fa fa-money");
        list.add("fa fa-moon-o");
        list.add("fa fa-mortar-board");
        list.add("fa fa-motorcycle");
        list.add("fa fa-music");
        list.add("fa fa-navicon");
        list.add("fa fa-neuter");
        list.add("fa fa-newspaper-o");
        list.add("fa fa-openid");
        list.add("fa fa-outdent");
        list.add("fa fa-pagelines");
        list.add("fa fa-paint-brush");
        list.add("fa fa-paper-plane");
        list.add("fa fa-paper-plane-o");
        list.add("fa fa-paperclip");
        list.add("fa fa-paragraph");
        list.add("fa fa-paste");
        list.add("fa fa-pause");
        list.add("fa fa-paw");
        list.add("fa fa-paypal");
        list.add("fa fa-pencil");
        list.add("fa fa-pencil-square");
        list.add("fa fa-pencil-square-o");
        list.add("fa fa-phone");
        list.add("fa fa-phone-square");
        list.add("fa fa-photo");
        list.add("fa fa-picture-o");
        list.add("fa fa-pie-chart");
        list.add("fa fa-pied-piper");
        list.add("fa fa-pied-piper-alt");
        list.add("fa fa-pinterest");
        list.add("fa fa-pinterest-p");
        list.add("fa fa-pinterest-square");
        list.add("fa fa-plane");
        list.add("fa fa-play");
        list.add("fa fa-play-circle");
        list.add("fa fa-play-circle-o");
        list.add("fa fa-plug");
        list.add("fa fa-plus");
        list.add("fa fa-plus-circle");
        list.add("fa fa-plus-square");
        list.add("fa fa-plus-square-o");
        list.add("fa fa-power-off");
        list.add("fa fa-print");
        list.add("fa fa-puzzle-piece");
        list.add("fa fa-qq");
        list.add("fa fa-qrcode");
        list.add("fa fa-question");
        list.add("fa fa-question-circle");
        list.add("fa fa-quote-left");
        list.add("fa fa-quote-right");
        list.add("fa fa-ra");
        list.add("fa fa-random");
        list.add("fa fa-rebel");
        list.add("fa fa-recycle");
        list.add("fa fa-reddit");
        list.add("fa fa-reddit-square");
        list.add("fa fa-refresh");
        list.add("fa fa-remove");
        list.add("fa fa-renren");
        list.add("fa fa-reorder");
        list.add("fa fa-repeat");
        list.add("fa fa-reply");
        list.add("fa fa-reply-all");
        list.add("fa fa-retweet");
        list.add("fa fa-rmb");
        list.add("fa fa-road");
        list.add("fa fa-rocket");
        list.add("fa fa-rotate-left");
        list.add("fa fa-rotate-right");
        list.add("fa fa-rouble");
        list.add("fa fa-rss");
        list.add("fa fa-rss-square");
        list.add("fa fa-rub");
        list.add("fa fa-ruble");
        list.add("fa fa-rupee");
        list.add("fa fa-save");
        list.add("fa fa-scissors");
        list.add("fa fa-search");
        list.add("fa fa-search-minus");
        list.add("fa fa-search-plus");
        list.add("fa fa-sellsy");
        list.add("fa fa-send");
        list.add("fa fa-send-o");
        list.add("fa fa-server");
        list.add("fa fa-share");
        list.add("fa fa-share-alt");
        list.add("fa fa-share-alt-square");
        list.add("fa fa-share-square");
        list.add("fa fa-share-square-o");
        list.add("fa fa-shekel");
        list.add("fa fa-sheqel");
        list.add("fa fa-shield");
        list.add("fa fa-ship");
        list.add("fa fa-shirtsinbulk");
        list.add("fa fa-shopping-cart");
        list.add("fa fa-sign-in");
        list.add("fa fa-sign-out");
        list.add("fa fa-signal");
        list.add("fa fa-simplybuilt");
        list.add("fa fa-sitemap");
        list.add("fa fa-skyatlas");
        list.add("fa fa-skype");
        list.add("fa fa-slack");
        list.add("fa fa-sliders");
        list.add("fa fa-slideshare");
        list.add("fa fa-smile-o");
        list.add("fa fa-soccer-ball-o");
        list.add("fa fa-sort");
        list.add("fa fa-sort-alpha-asc");
        list.add("fa fa-sort-alpha-desc");
        list.add("fa fa-sort-amount-asc");
        list.add("fa fa-sort-amount-desc");
        list.add("fa fa-sort-asc");
        list.add("fa fa-sort-desc");
        list.add("fa fa-sort-down");
        list.add("fa fa-sort-numeric-asc");
        list.add("fa fa-sort-numeric-desc");
        list.add("fa fa-sort-up");
        list.add("fa fa-soundcloud");
        list.add("fa fa-space-shuttle");
        list.add("fa fa-spinner");
        list.add("fa fa-spoon");
        list.add("fa fa-spotify");
        list.add("fa fa-square");
        list.add("fa fa-square-o");
        list.add("fa fa-stack-exchange");
        list.add("fa fa-stack-overflow");
        list.add("fa fa-star");
        list.add("fa fa-star-half");
        list.add("fa fa-star-half-empty");
        list.add("fa fa-star-half-full");
        list.add("fa fa-star-half-o");
        list.add("fa fa-star-o");
        list.add("fa fa-steam");
        list.add("fa fa-steam-square");
        list.add("fa fa-step-backward");
        list.add("fa fa-step-forward");
        list.add("fa fa-stethoscope");
        list.add("fa fa-stop");
        list.add("fa fa-street-view");
        list.add("fa fa-strikethrough");
        list.add("fa fa-stumbleupon");
        list.add("fa fa-stumbleupon-circle");
        list.add("fa fa-subscript");
        list.add("fa fa-subway");
        list.add("fa fa-suitcase");
        list.add("fa fa-sun-o");
        list.add("fa fa-superscript");
        list.add("fa fa-support");
        list.add("fa fa-table");
        list.add("fa fa-tablet");
        list.add("fa fa-tachometer");
        list.add("fa fa-tag");
        list.add("fa fa-tags");
        list.add("fa fa-tasks");
        list.add("fa fa-taxi");
        list.add("fa fa-tencent-weibo");
        list.add("fa fa-terminal");
        list.add("fa fa-text-height");
        list.add("fa fa-text-width");
        list.add("fa fa-th");
        list.add("fa fa-th-large");
        list.add("fa fa-th-list");
        list.add("fa fa-thumb-tack");
        list.add("fa fa-thumbs-down");
        list.add("fa fa-thumbs-o-down");
        list.add("fa fa-thumbs-o-up");
        list.add("fa fa-thumbs-up");
        list.add("fa fa-ticket");
        list.add("fa fa-times");
        list.add("fa fa-times-circle");
        list.add("fa fa-times-circle-o");
        list.add("fa fa-tint");
        list.add("fa fa-toggle-down");
        list.add("fa fa-toggle-left");
        list.add("fa fa-toggle-off");
        list.add("fa fa-toggle-on");
        list.add("fa fa-toggle-right");
        list.add("fa fa-toggle-up");
        list.add("fa fa-train");
        list.add("fa fa-transgender");
        list.add("fa fa-transgender-alt");
        list.add("fa fa-trash");
        list.add("fa fa-trash-o");
        list.add("fa fa-tree");
        list.add("fa fa-trello");
        list.add("fa fa-trophy");
        list.add("fa fa-truck");
        list.add("fa fa-try");
        list.add("fa fa-tty");
        list.add("fa fa-tumblr");
        list.add("fa fa-tumblr-square");
        list.add("fa fa-turkish-lira");
        list.add("fa fa-twitch");
        list.add("fa fa-twitter");
        list.add("fa fa-twitter-square");
        list.add("fa fa-umbrella");
        list.add("fa fa-underline");
        list.add("fa fa-undo");
        list.add("fa fa-university");
        list.add("fa fa-unlink");
        list.add("fa fa-unlock");
        list.add("fa fa-unlock-alt");
        list.add("fa fa-unsorted");
        list.add("fa fa-upload");
        list.add("fa fa-usd");
        list.add("fa fa-user");
        list.add("fa fa-user-md");
        list.add("fa fa-user-plus");
        list.add("fa fa-user-secret");
        list.add("fa fa-user-times");
        list.add("fa fa-users");
        list.add("fa fa-venus");
        list.add("fa fa-venus-double");
        list.add("fa fa-venus-mars");
        list.add("fa fa-viacoin");
        list.add("fa fa-video-camera");
        list.add("fa fa-vimeo-square");
        list.add("fa fa-vine");
        list.add("fa fa-vk");
        list.add("fa fa-volume-down");
        list.add("fa fa-volume-off");
        list.add("fa fa-volume-up");
        list.add("fa fa-warning");
        list.add("fa fa-wechat");
        list.add("fa fa-weibo");
        list.add("fa fa-weixin");
        list.add("fa fa-whatsapp");
        list.add("fa fa-wheelchair");
        list.add("fa fa-wifi");
        list.add("fa fa-windows");
        list.add("fa fa-won");
        list.add("fa fa-wordpress");
        list.add("fa fa-wrench");
        list.add("fa fa-xing");
        list.add("fa fa-xing-square");
        list.add("fa fa-yahoo");
        list.add("fa fa-yelp");
        list.add("fa fa-yen");
        list.add("fa fa-youtube");
        list.add("fa fa-youtube-play");
        return list;
    }

    public List<Module> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<Module> moduleList) {
        this.moduleList = moduleList;
    }

    public List<Module> getFilteredModule() {
        return filteredModule;
    }

    public void setFilteredModule(List<Module> filteredModule) {
        this.filteredModule = filteredModule;
    }

    public List<Module> getModules() {
        return modules;
    }

    public List<String> getListIcons() {
        return listIcons;
    }

    public void setListIcons(List<String> listIcons) {
        this.listIcons = listIcons;
    }
}
