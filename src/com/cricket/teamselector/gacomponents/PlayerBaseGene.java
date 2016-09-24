/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cricket.teamselector.gacomponents;

import com.cricket.teamselector.data.StatisticsProvider;
import com.cricket.teamselector.entity.Player;
import com.cricket.teamselector.utils.RandomPlayerGenerator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jgap.BaseGene;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.RandomGenerator;
import org.jgap.UnsupportedRepresentationException;

/**
 *
 * @author ZenitH
 */
public class PlayerBaseGene extends BaseGene implements Gene, java.io.Serializable {

    protected Player player;
    private String speciality;

    public PlayerBaseGene(Configuration a_configuration, String speciality) throws InvalidConfigurationException {
        super(a_configuration);
        this.speciality = speciality;
    }

    @Override
    protected Gene newGeneInternal() {
        Gene g = null;
        try {
            return g = new PlayerBaseGene(getConfiguration(), this.speciality);

        } catch (InvalidConfigurationException ex) {

            Logger.getLogger(PlayerBaseGene.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
    }

    @Override
    public String getPersistentRepresentation() throws UnsupportedOperationException {
        return Integer.toString(this.player.getPlayerID());

    }

    @Override
    public void setValueFromPersistentRepresentation(String idstring) throws UnsupportedOperationException, UnsupportedRepresentationException {
        int id = Integer.parseInt(idstring);
        StatisticsProvider p = new StatisticsProvider();
        try {
            this.player = p.getBatsmanById(id);
        } catch (Exception ex) {
            Logger.getLogger(PlayerBaseGene.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void setAllele(Object a_newValue) {
          this.player = (Player) a_newValue;
    }

    @Override
    public Object getAllele() {
        return this.player;
    }

    @Override
    protected Object getInternalValue() {
        return player;
    }

    @Override
    public void setToRandomValue(RandomGenerator rg) {
        if (speciality == "batsman") {
            // DB call to get the size of batsman list
            int batsmanSize = new StatisticsProvider().getBatsmanIDSize();
            //setAllele(i);
            int rand = rg.nextInt(batsmanSize);
            try {
                this.player = new StatisticsProvider().getBatsmanById(rand);
            } catch (Exception ex) {
                Logger.getLogger(PlayerBaseGene.class.getName()).log(Level.SEVERE, null, ex);
            }
            setAllele(player);
        } else if (speciality == "bowler") {
            int bowler = new StatisticsProvider().getBowlerIDSize();
            int rand = rg.nextInt(bowler);
            try {
                this.player = new StatisticsProvider().getBowlerById(rand);
            } catch (Exception ex) {
                Logger.getLogger(PlayerBaseGene.class.getName()).log(Level.SEVERE, null, ex);
            }
            setAllele(player);
        } else if (speciality == "wicketkeeper") {
            int wicketkeeperSize = new StatisticsProvider().getWicketKeeperIDSize();
            int rand = rg.nextInt(wicketkeeperSize);
            try {
                this.player = new StatisticsProvider().getWicketKeeperById(rand);
            } catch (Exception ex) {
                Logger.getLogger(PlayerBaseGene.class.getName()).log(Level.SEVERE, null, ex);
            }
             setAllele(player);
        } else if (speciality == "Allrounder") {
            int allrounderSize = new StatisticsProvider().getAllrounderIDSize();
            int rand = rg.nextInt(allrounderSize);
         
            try {
                this.player = new StatisticsProvider().getAllrounderById(rand);
            } catch (Exception ex) {
                Logger.getLogger(PlayerBaseGene.class.getName()).log(Level.SEVERE, null, ex);
            }
             setAllele(player);
        }


        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void applyMutation(int i, double d) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int compareTo(Object o) {

//        Player p = (Player) o;
//        if (p.getIsbatsman()== 1)
//        {
//            
//        }
//         int a =0;
//         return a;
        return 0;
    }
    // checks whether the two ids are equal
    public boolean equalsCustom(PlayerBaseGene other) {
        return this.player.getPlayerID()==other.player.getPlayerID(); //To change body of generated methods, choose Tools | Templates.
    }
}
