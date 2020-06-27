package com.greg.mareu.service;

import com.greg.mareu.model.Reunion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class DummyReunionGenerator
{
   public static Date setReunionDate(int year, int month, int day){
      Calendar c = Calendar.getInstance();
      c.set(Calendar.YEAR, year);
      c.set(Calendar.MONTH, month);
      c.set(Calendar.DAY_OF_MONTH, day);
      c.set(Calendar.HOUR_OF_DAY, 0);
      c.set(Calendar.MINUTE, 0);
      c.set(Calendar.SECOND, 0);
      c.set(Calendar.MILLISECOND, 0);

      return c.getTime();
   }

   public static List<Reunion> DUMMY_REUNION = Arrays.asList(
           new Reunion(1, "https://www.lithotherapie.net/wp-content/uploads/2010/03/nettoyage_purification_pierres_cristaux_lithotherapie.jpg","La lithothérapie",setReunionDate(2020,6, 15), "14h30", "16h15","Salle Lion", "ares@lamzone.com, horus@lamzone.com, poseidon@lamzone.com"),
           new Reunion(2, "https://wallpapercave.com/wp/wc1676091.jpg","Le Milan AC", setReunionDate(2020,6, 23), "9h30", "11h","Salle Capricorne", "seth@lamzone.com, osiris@lamzone.com, zeus@lamzone.com"),
           new Reunion(3, "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3a/San_Francisco_49ers_logo.svg/1200px-San_Francisco_49ers_logo.svg.png","Les 49ers", setReunionDate(2020,6, 27), "16h", "17h30","Salle Cancer", "dionysos@lamzone.com, hermes@lamzone.com, ganesh@lamzone.com"),
           new Reunion(4, "https://yournexttireblogdotcom1.files.wordpress.com/2016/02/anaheim-ducks-logo-700.png","Les Ducks d'Anaheim", setReunionDate(2020,6, 31), "15h30", "16h30","Salle Gémeaux", "shiva@lamzone.com, kali@lamzone.com, baastet@lamzone.com"),
           new Reunion(5, "https://upload.wikimedia.org/wikipedia/commons/2/22/KBryant8.jpg","Les Lakers", setReunionDate(2020,7, 5), "11h30", "12h30","Salle Taureau", "quetzacoatl@lamzone.com, gibil@lamzone.com, marduk@lamzone.com"),
           new Reunion(6, "https://1d4chan.org/images/9/9b/Solid_Snake_codec.png","Solid Snake", setReunionDate(2020,7, 7), "13h30", "15h15","Salle Vierge", "wi@lamzone.com, athena@lamzone.com, hades@lamzone.com"),
           new Reunion(7, "https://vignette.wikia.nocookie.net/walkingdead/images/7/74/TWD_Season_10_poster.jpg/revision/latest?cb=20190720160224","The walking Dead", setReunionDate(2020,7, 15), "8h", "10h30","Salle Verseau", "inyan@lamzone.com, anubis@lamzone.com, khnoum@lamzone.com"),
           new Reunion(8, "https://assets1.ignimgs.com/2015/03/11/vvunnamedjpg-52a841_1280w.jpg","Vikings", setReunionDate(2020,7, 23), "11h30", "12h45","Salle Sagittaire", "thor@lamzone.com, odin@lamzone.com, loki@lamzone.com"),
           new Reunion(9, "https://d1lss44hh2trtw.cloudfront.net/assets/editorial/2018/10/geraltphotoshop.jpg","The Witchers", setReunionDate(2020,7, 24), "14h", "15h30","Salle Poisson", "freya@lamzone.com, hanuman@lamzone.com, brahma@lamzone.com"),
           new Reunion(10, "https://i.stack.imgur.com/YuMqS.jpg","L'exploration de Mars", setReunionDate(2020,7, 25), "16h", "16h45","Salle Bélier", "tlaloc@lamzone.com, apollon@lamzone.com, hephaistos@lamzone.com"),
           new Reunion(11, "https://www.cen.eu/work/areas/Nanotech/PublishingImages/Nanotechnologies.jpg","Les nanotechnologies", setReunionDate(2020,7, 27), "10h30", "11h45","Salle Bélier", "hera@lamzone.com, geb@lamzone.com, nout@lamzone.com"),
           new Reunion(12, "https://i.ytimg.com/vi/zmpY2yXMIxc/maxresdefault.jpg","Fabriquer un sabre laser", setReunionDate(2020,8, 5), "11h", "11h30","Salle Lion", "vishnu@lamzone.com, xolotl@lamzone.com, huitzilopoctli@lamzone.com"),
           new Reunion(13, "https://is3-ssl.mzstatic.com/image/thumb/Music3/v4/51/83/12/518312fd-ab5f-4669-8620-c71ce78c2274/source/3000x3000sr.jpg","Dexter", setReunionDate(2020,8, 6), "14h15", "15h30","Salle Verseau", "watatsumi@lamzone.com, ishtar@lamzone.com, toth@lamzone.com"),
           new Reunion(14, "https://i.ytimg.com/vi/XEq-Y46Tlxg/maxresdefault.jpg","Dark Vador", setReunionDate(2020,8, 9), "9h30", "11h15","Salle Vierge", "sobek@lamzone.com, ra@lamzone.com, taouret@lamzone.com"),
           new Reunion(15, "https://www.visitourchina.com/FileUpload/FileUpload/100112142321564.jpg","La bataille de Chi Bi", setReunionDate(2020,8, 22), "13h45", "16h30","Salle Lion", "sekhmet@lamzone.com, api@lamzone.com, parvati@lamzone.com")
   );

   static List<Reunion> generateReunions()
   {
       return new ArrayList<>(DUMMY_REUNION);
   }
}
