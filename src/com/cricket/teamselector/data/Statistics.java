/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cricket.teamselector.data;
import com.cricket.teamselector.app.Gens;
import com.cricket.teamselector.entity.Player;
import com.cricket.teamselector.utils.PlayerListItem;
import java.util.List;

/**
 *
 * @author zenith
 */
public interface Statistics {

    public List<Gens> getPlayers(long butget);
    public float getMax(String role);
    public void setTemporaryPlayerRecords(List<String> playerCodes, List<String> years, String RecordType) throws Exception  ;

    public Player getPlayerDetails(String playerCode, String year, String recordType) throws Exception ;

    public void calculateStats(List<Player> playerWiseList) throws Exception ;

    public void setPlayerDetailsToTemporaryTable(Player player) throws Exception ;

    public void addToTemporaryTable(String spName, Player player) throws Exception ;

    public int getBatsmanIDSize() throws Exception ;

    public int getBowlerIDSize() throws Exception ;

    public int getWicketKeeperIDSize() throws Exception ;

    public int getAllrounderIDSize() throws Exception ;

    public Player getAllrounderById(int playerID) throws Exception ;

    public Player getBatsmanById(int playerID) throws Exception ;

    public Player getBowlerById(int playerID) throws Exception ;

    public Player getWicketKeeperById(int playerID) throws Exception ;
    
    public int getHighestBattedInnings() throws Exception ;

    public int getHighestBallsFaceed() throws Exception ;

    public int getHighestNumberOfBallsBowled() throws Exception ;

    public List<PlayerListItem> getPlayerListItemCollectionBySpecialty(String playerType) throws Exception ;

    public String getPlayerTypeById(int playerID) throws Exception ;
    
    public int getHighestNumberOfMatchesPlayed() throws Exception;

}
