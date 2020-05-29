package com.greg.mareu.service;

import com.greg.mareu.model.Reunion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public abstract class DummyReunionGenerator
{
   public static List<Reunion> DUMMY_REUNION = Arrays.asList(
           new Reunion(1, "https://www.lithotherapie.net/wp-content/uploads/2010/03/nettoyage_purification_pierres_cristaux_lithotherapie.jpg","La lithothérapie",new Date(120, 5, 15), "14h30", "16h15","Salle Lion", "ares@lamzone.com, horus@lamzone.com, poseidon@lamzone.com"),
           new Reunion(2, "https://wallpapercave.com/wp/wc1676091.jpg","Le Milan AC",new Date(120, 5, 23), "9h30", "11h","Salle Capricorne", "seth@lamzone.com, osiris@lamzone.com, zeus@lamzone.com"),
           new Reunion(3, "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3a/San_Francisco_49ers_logo.svg/1200px-San_Francisco_49ers_logo.svg.png","Les 49ers",new Date(120, 5, 27), "16h", "17h30","Salle Cancer", "dionysos@lamzone.com, hermes@lamzone.com, ganesh@lamzone.com"),
           new Reunion(4, "https://yournexttireblogdotcom1.files.wordpress.com/2016/02/anaheim-ducks-logo-700.png","Les Ducks d'Anaheim",new Date(120, 5, 31), "15h30", "16h30","Salle Gémeaux", "shiva@lamzone.com, kali@lamzone.com, baastet@lamzone.com"),
           new Reunion(5, "https://upload.wikimedia.org/wikipedia/commons/2/22/KBryant8.jpg","Les Lakers",new Date(120, 6, 4), "11h30", "12h30","Salle Taureau", "quetzacoatl@lamzone.com, gibil@lamzone.com, marduk@lamzone.com"),
           new Reunion(6, "https://1d4chan.org/images/9/9b/Solid_Snake_codec.png","Solid Snake",new Date(120, 6, 7), "13h30", "15h15","Salle Vierge", "wi@lamzone.com, athena@lamzone.com, hades@lamzone.com"),
           new Reunion(7, "https://vignette.wikia.nocookie.net/walkingdead/images/7/74/TWD_Season_10_poster.jpg/revision/latest?cb=20190720160224","The walking Dead",new Date(120, 6, 15), "8h", "10h30","Salle Verseau", "inyan@lamzone.com, anubis@lamzone.com, khnoum@lamzone.com"),
           new Reunion(8, "https://assets1.ignimgs.com/2015/03/11/vvunnamedjpg-52a841_1280w.jpg","Vikings",new Date(120, 6, 23), "11h30", "12h45","Salle Sagittaire", "thor@lamzone.com, odin@lamzone.com, loki@lamzone.com"),
           new Reunion(9, "https://img-cdn.hipertextual.com/files/2019/04/hipertextual-fecha-estreno-the-witcher-mas-cerca-se-espera-2019-2019564689.jpg?strip=all&lossy=1&quality=70&resize=740%2C490&ssl=1","The Witchers",new Date(120, 6, 24), "14h", "15h30","Salle Poisson", "freya@lamzone.com, hanuman@lamzone.com, brahma@lamzone.com"),
           new Reunion(10, "https://i.stack.imgur.com/YuMqS.jpg","L'exploration de Mars",new Date(120, 6, 24), "16h", "16h45","Salle Bélier", "tlaloc@lamzone.com, apollon@lamzone.com, hephaistos@lamzone.com"),
           new Reunion(11, "https://www.cen.eu/work/areas/Nanotech/PublishingImages/Nanotechnologies.jpg","Les nanotechnologies",new Date(120, 6, 25), "10h30", "11h45","Salle Bélier", "hera@lamzone.com, geb@lamzone.com, nout@lamzone.com"),
           new Reunion(12, "https://i.ytimg.com/vi/zmpY2yXMIxc/maxresdefault.jpg","Fabriquer un sabre laser",new Date(120, 7, 5), "11h", "11h30","Salle Lion", "vishnu@lamzone.com, xolotl@lamzone.com, huitzilopoctli@lamzone.com"),
           new Reunion(13, "https://is3-ssl.mzstatic.com/image/thumb/Music3/v4/51/83/12/518312fd-ab5f-4669-8620-c71ce78c2274/source/3000x3000sr.jpg","Dexter",new Date(120, 7, 6), "14h15", "15h30","Salle Verseau", "watatsumi@lamzone.com, ishtar@lamzone.com, toth@lamzone.com"),
           new Reunion(14, "https://i.ytimg.com/vi/XEq-Y46Tlxg/maxresdefault.jpg","Dark Vador",new Date(120, 7, 9), "9h30", "11h15","Salle Vierge", "sobek@lamzone.com, ra@lamzone.com, taouret@lamzone.com"),
           new Reunion(15, "https://www.visitourchina.com/FileUpload/FileUpload/100112142321564.jpg","La bataille de Chi Bi",new Date(120, 7, 21), "13h45", "16h30","Salle Lion", "sekhmet@lamzone.com, api@lamzone.com, parvati@lamzone.com")
   );

   static List<Reunion> generateReunions()
   {
       return new ArrayList<>(DUMMY_REUNION);
   }
}
