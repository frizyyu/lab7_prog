package helpers;

import supportive.Coordinates;
import supportive.MusicBand;
import supportive.MusicGenre;
import supportive.Studio;

import java.util.Scanner;

/**
 * Class for create instance with user-friendly questions
 *
 * @author frizyy
 */
public class UserFriendlyCreateObject {
    /**
     *
     * @param myMap instance of MusicBand (not required)
     * @return instance of MusicBand
     */
    public MusicBand create(MusicBand myMap){
        myMap = new MusicBand();
        int fieldsCounter = 0;
        Coordinates toAdd = new Coordinates(null, 0);
        Studio toAddStud = new Studio(null, null);
        Scanner input = new Scanner(System.in);
        while (fieldsCounter < 6){
            if (myMap.getName() == null) {
                System.out.print("Enter name: ");
                String name = input.nextLine();
                if (name.equals(""))
                    name = null;
                if (name != null && !name.isEmpty()) {
                    myMap.setName(name);
                    System.out.println("Name added\n");
                    fieldsCounter += 1;
                }
                else {
                    System.out.println("Invalid argument. Expected String\n");
                    continue;
                }
            }

            if (myMap.getCoordinates() == null || (toAdd.getX() == null || toAdd.getY() == 0)) {
                try {
                    if (toAdd.getX() == null) {
                        String coordsX;
                        System.out.print("Enter Coordinates X: ");
                        coordsX = input.nextLine();
                        if (coordsX.equals(""))
                            coordsX = null;
                        if (coordsX != null && Long.parseLong(coordsX) <= 268) {
                            toAdd.setX(Long.parseLong(coordsX));
                            myMap.setCoordinates(toAdd);
                            System.out.println("X added\n");
                        }else {
                            System.out.println("Invalid argument. Expected Double, <= 55\n");
                            continue;
                        }
                    }

                    if (toAdd.getY() == 0) {
                        String coordsY;
                        System.out.print("Enter Coordinates Y: ");
                        coordsY = input.nextLine();
                        //System.out.print("Y: ");
                        //System.out.println(coordsY);
                        if (Double.parseDouble(coordsY) <= 55) {
                            toAdd.setY(Double.parseDouble(coordsY));
                            myMap.setCoordinates(toAdd);
                            System.out.println("Y added\n");
                        }else {
                            System.out.println("Invalid argument. Expected Double, <= 55\n");
                            continue;
                        }
                    }
                    Coordinates coords = new Coordinates(toAdd.getX(), toAdd.getY());
                    myMap.setCoordinates(coords);
                    fieldsCounter += 1;
                } catch (NumberFormatException number) {
                    System.out.println("Invalid value type. For X expected Long, for Y Double\n");
                    continue;
                }
            }

            if (myMap.getNumberOfParticipants() == 0) {
                try {
                    System.out.print("Enter numberOfParticipants: ");
                    String numberOfParticipants = input.nextLine();
                    if (numberOfParticipants.equals(""))
                        numberOfParticipants = null;
                    if (Integer.parseInt(numberOfParticipants) > 0) {
                        myMap.setNumberOfParticipants(Integer.parseInt(numberOfParticipants));
                        System.out.println("numberOfParticipants added\n");
                        fieldsCounter += 1;
                    } else{
                        System.out.println("Invalid argument. Expected int, > 0\n");
                        continue;
                    }
                } catch (NumberFormatException number) {
                    System.out.println("Invalid value type. Expected int\n");
                    continue;
                }
            }

            if (myMap.getAlbumsCount() == null) {
                try {
                    System.out.print("Enter albumsCount: ");
                    String albumsCount = input.nextLine();
                    if (albumsCount.equals(""))
                        albumsCount = null;
                    if (albumsCount != null && Integer.parseInt(albumsCount) > 0) {
                        myMap.setAlbumsCount(Integer.valueOf(albumsCount));
                        System.out.println("albumsCount added\n");
                        fieldsCounter += 1;;
                    } else{
                        System.out.println("Invalid argument. Expected Integer, >0\n");
                        continue;
                    }
                } catch (NumberFormatException number) {
                    System.out.println("Invalid value type. Expected Integer\n");
                    continue;
                }
            }

            if (myMap.getGenre() == null) {
                try {
                    System.out.print("Enter genre (Expected one of RAP, HIP_HOP, JAZZ, POST_PUNK, BRIT_POP): ");
                    String genre = input.nextLine();
                    if (genre.equals(""))
                        genre = null;
                    myMap.setGenre(MusicGenre.valueOf(genre.toUpperCase()));
                    System.out.println("genre added\n");
                    fieldsCounter += 1;
                } catch (Exception enumm) {
                    System.out.println("Invalid value type. Expected one of RAP, HIP_HOP, JAZZ, POST_PUNK, BRIT_POP\n");
                    continue;
                }
            }

            if (myMap.getStudio() == null || (toAddStud.getName() == null || toAddStud.getAddress() == null)) {
                try {
                    String name = null;
                    if (toAddStud.getName() == null) {
                        System.out.print("Enter Studio name: ");
                        name = input.nextLine();
                        if (name.equals(""))
                            name = null;
                        if (name != null) {
                            toAddStud.setName(name);
                            System.out.println("name added\n");
                        } else {
                            System.out.println("Invalid argument. Expected String, not null\n");
                            continue;
                        }
                    }
                    String address = null;
                    if (toAddStud.getAddress() == null) {
                        System.out.print("Enter Studio address: ");
                        address = input.nextLine();
                        if (address.equals(""))
                            address = null;
                        if (address != null) {
                            toAddStud.setAddress(address);
                            System.out.println("address added\n");
                        } else {
                            System.out.println("Invalid argument. Expected String, not null\n");
                            continue;
                        }
                    }
                    Studio stud = new Studio(toAddStud.getName(), toAddStud.getAddress());
                    myMap.setStudio(stud);
                    fieldsCounter += 1;
                } catch (NumberFormatException number) {
                    System.out.println("Invalid value type. Expected String\n");
                }
            }
        }
        return myMap;
    }
}
