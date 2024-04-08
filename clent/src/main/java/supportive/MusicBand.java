package supportive;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class MusicBand implements Serializable {
    private static final long serialVersionUID = 111L;
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int numberOfParticipants; //Значение поля должно быть больше 0
    private Integer albumsCount; //Поле не может быть null, Значение поля должно быть больше 0
    private MusicGenre genre; //Поле не может быть null
    private Studio studio; //Поле не может быть null

    public long getId(){
        return id;
    }
    public String getName() {
        return name;
    }
    public Coordinates getCoordinates(){
        return coordinates;
    }
    public ZonedDateTime getCreationDate(){
        return creationDate;
    }
    public int getNumberOfParticipants(){
        return numberOfParticipants;
    }
    public Integer getAlbumsCount(){
        return albumsCount;
    }
    public MusicGenre getGenre(){
        return genre;
    }
    public Studio getStudio(){
        return studio;
    }

    public void setId(long id){
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCoordinates(Coordinates coordinates){
        this.coordinates = coordinates;
    }
    public void setCreationDate(ZonedDateTime creationDate){
        this.creationDate = creationDate;
    }
    public void setNumberOfParticipants(int numberOfParticipants){
        this.numberOfParticipants = numberOfParticipants;
    }
    public void setAlbumsCount(Integer albumsCount){
        this.albumsCount = albumsCount;
    }
    public void setGenre(MusicGenre genre){
        this.genre = genre;
    }
    public void setStudio(Studio studio){
        this.studio = studio;
    }
}
