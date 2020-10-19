package traininglogger.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Et exercise-objekt skal ta vare på informasjonen rundt en øvelse.
 * Objektet inneholder informanjon om:
 * - Øvelsens navn
 * - sett
 */
public class Exercise {

  private List<Set> sets = new ArrayList<Set>();
  private String exerciseName;

  /**
   * Tom konstruktør som kun instansierer et objekt uten noe informasjon
   */
  public Exercise() {

  }
  
  /**
   * Konstruktør som instansierer objektet med navn på øvelsen og en array med sett.
   * @param name Et navn på øvelsen.
   * @param sets En array med set som objektet skal bestå av.
   */
  public Exercise(String name, Set... sets) {
    this.exerciseName = name;
    this.addSets(sets);
  }

  /**
   * Metode for å legge til sett til exercise-objektet. 
   *
   * @param sets Tar inn et array av sett.
   */
  public void addSets(Set...sets) {
    this.sets.addAll(Arrays.asList(sets));
  }

  /**
   * Fjerner sett nr. i fra liten med sett.
   * Fjerner et sett basert på indexen som tas inn.
   *
   * @param i indeksen til settet som skal fjernes
   */
  public void removeSet(int i) {
    this.sets.remove(i);
  }

  /**
   * @param i
   * @return Sett nr. i
   */
  public Set getSet(int i) {
    return this.sets.get(i);
  }

  /**
   * 
   * @return Henter navn på øvelsen.
   */
  public String getName() {
    return this.exerciseName;
  }

  /**
   * 
   * @param name Setter navn på øvelsen.
   */
  public void setName(String name) {
    this.exerciseName = name;
  }

  /**
   * 
   * @return En liste med settene.
   */
  public List<Set> getSets() {
    return this.sets.stream().collect(Collectors.toList());
  }

  /**
   * Sammenlikner dette objektet med object.
   * @param object Objektet instansen skal sammenliknes med
   * @return Returnerer true dersom objektene har de samme settene, i samme rekkefølge, og det samme navnet.
   */
  public boolean equals(Object object) {
    if(!(object instanceof Exercise)){
      return false;
    }
    Exercise obj = (Exercise) object;
    return this.getName().equals(obj.getName()) && this.sets.equals(obj.getSets());
  }

  // Denne implementasjonen er bare anbefalt dersom man aldri ser for seg å
  // plassere exercise-objekter
  // i et HashMap eller en HashTable.
  @Override
  public int hashCode() {
    assert false : "hashCode not designed";
    return 1;
  }

}