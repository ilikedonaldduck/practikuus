package suka.ok.presidents;

public class NewPresidents extends Presidents {
    class newPresidents {
        public void assad() {
            nameofpresident = "assad";
            year = 59;
            birthday = 1965;
            country = "syria";
            hiword = "Salam Aleykum!";
        }

    }

    public void hiwords() {
        newPresidents np = new newPresidents();
        np.assad();
        System.out.println(hiword + " my name is " + nameofpresident + " from " + country + " and i was born in " + birthday + " i " + year + " years old");
    }
}
