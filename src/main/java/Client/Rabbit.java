package main.java.Client;

import java.util.ArrayList;

public class Rabbit extends Animal
{
    Rabbit()
    {
        super();
    }

    @Override
    public char getType()
    {
        return 'R';
    }

    @Override
    public int[] move(int i, int j, Animal[][] map)
    {
        int[] tmp = new int[2];

        ArrayList<int[]> pos = new ArrayList<>();

        for(int i1 = i - 1; i1 < i + 2; i1++)
            for(int j1 = j - 1; j1 < j + 2; j1++)
                if(i1 > -1 && i1 < 20 && j1 > -1 && j1 < 20 && (j1 != j || i1 != i) && map[i1][j1].getType() == 'A')
                    pos.add(new int[]{i1, j1});


        if(pos.size() == 0)
        {
            tmp[0] = i;
            tmp[1] = j;
        }
        else
        {
            int o = (int)(Math.random() * pos.size());
            tmp[0] = pos.get(o)[0];
            tmp[1] = pos.get(o)[1];
        }


        return tmp;
    }
}
