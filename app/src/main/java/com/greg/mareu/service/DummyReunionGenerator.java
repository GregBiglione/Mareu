package com.greg.mareu.service;

import com.greg.mareu.model.Reunion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyReunionGenerator
{
   public static List<Reunion> DUMMY_REUNION = Arrays.asList(
           new Reunion(1, "https://www.lithotherapie.net/wp-content/uploads/2010/03/nettoyage_purification_pierres_cristaux_lithotherapie.jpg","La lithothérapie","15 mai 2020", "14h30", "Salle Lion", "ares@gmail.com, horus@gmail.com, poseidon@gmail.com"),
           new Reunion(2, "https://wallpapercave.com/wp/wc1676091.jpg","Le Milan AC","17 mai 2020", "9h30", "Salle Capricorne", "seth@gmail, osiris@gmail.com, zeus@gmail.com"),
           new Reunion(3, "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3a/San_Francisco_49ers_logo.svg/1200px-San_Francisco_49ers_logo.svg.png","Les 49ers","19 mai 2020", "16h", "Salle Cancer", "dionysos@gmail, hermes@gmail.com, ganesh@gmail.com"),
           new Reunion(4, "https://yournexttireblogdotcom1.files.wordpress.com/2016/02/anaheim-ducks-logo-700.png","Les Ducks d'Anaheim","23 mai 2020", "15h30", "Salle Gémeaux", "shiva@gmail, cali@gmail.com, baastet@gmail.com"),
           new Reunion(5, "https://upload.wikimedia.org/wikipedia/commons/2/22/KBryant8.jpg","Les Lakers","30 mai 2020", "11h30", "Salle Taureau", "quetzacoatl@gmail, gibil@gmail.com, marduk@gmail.com"),
           new Reunion(6, "https://1d4chan.org/images/9/9b/Solid_Snake_codec.png","Solid Snake","3 juin 2020", "13h30", "Salle Vierge", "wi@gmail, athena@gmail.com, hades@gmail.com"),
           new Reunion(7, "https://vignette.wikia.nocookie.net/walkingdead/images/7/74/TWD_Season_10_poster.jpg/revision/latest?cb=20190720160224","The walking Dead","5 juin 2020", "8h", "Salle Verseau", "inyan@gmail, anubis@gmail.com, khnoum@gmail.com"),
           new Reunion(8, "https://assets1.ignimgs.com/2015/03/11/vvunnamedjpg-52a841_1280w.jpg","Vikings","8 juin 2020", "11h30", "Salle Sagittaire", "thor@gmail, odin@gmail.com, loki@gmail.com"),
           new Reunion(9, "https://img-cdn.hipertextual.com/files/2019/04/hipertextual-fecha-estreno-the-witcher-mas-cerca-se-espera-2019-2019564689.jpg?strip=all&lossy=1&quality=70&resize=740%2C490&ssl=1","The Witchers","17 juin 2020", "14h", "Salle Poisson", "freya@gmail, hanuman@gmail.com, brahma@gmail.com"),
           new Reunion(10, "https://i.stack.imgur.com/YuMqS.jpg","L'exploration de Mars","23 juin 2020", "16h", "Salle Bélier", "tlaloc@gmail, apollon@gmail.com, hephaistos@gmail.com"),
           new Reunion(11, "https://www.cen.eu/work/areas/Nanotech/PublishingImages/Nanotechnologies.jpg","Les nanotechnologies","26 juin 2020", "10h30", "Salle Bélier", "hera@gmail, geb@gmail.com, nout@gmail.com"),
           new Reunion(12, "https://i.ytimg.com/vi/zmpY2yXMIxc/maxresdefault.jpg","Fabriquer un sabre laser","28 juin 2020", "11h", "Salle Lion", "vishnu@gmail, xolotl@gmail.com, huitzilopoctli@gmail.com"),
           new Reunion(13, "https://is3-ssl.mzstatic.com/image/thumb/Music3/v4/51/83/12/518312fd-ab5f-4669-8620-c71ce78c2274/source/3000x3000sr.jpg","Dexter","4 juillet 2020", "14h15", "Salle Verseau", "watatsumi@gmail, ishtar@gmail.com, toth@gmail.com"),
           new Reunion(14, "https://i.ytimg.com/vi/XEq-Y46Tlxg/maxresdefault.jpg","Dark Vador","7 juillet 2020", "9h30", "Salle Vierge", "sobek@gmail, ra@gmail.com, taouret@gmail.com"),
           new Reunion(15, "https://www.visitourchina.com/FileUpload/FileUpload/100112142321564.jpg","La bataille de Chi Bi","11 juillet 2020", "13h45", "Salle Lion", "sekhmet@gmail, api@gmail.com, parvati@gmail.com")
   );

   static List<Reunion> generateReunions()
   {
       return new ArrayList<>(DUMMY_REUNION);
   }
}
