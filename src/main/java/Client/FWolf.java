package main.java.Client;

import java.util.ArrayList;

public class FWolf extends Animal
{
    FWolf()
    {
        super();
        this.hungry = 1;
    }

    @Override
    public char getType()
    {
        return 'F';
    }

    @Override
    public boolean isDeath()
    {
        this.hungry -= 0.1;
        return this.hungry <= 0;
    }

    @Override
    public void eat()
    {
        this.hungry += 1;
    }

    @Override
    public float currentHungry()
    {
        return this.hungry;
    }

    @Override
    public int[] move(int i, int j, Animal[][] map)
    {
        int[] tmp = new int[2];

        ArrayList<int[]> posRabbit = new ArrayList<>();
        ArrayList<int[]> pos = new ArrayList<>();

        for(int i1 = i-1; i1 < i+2; i1++)
            for(int j1 = j-1; j1 < j+2; j1++)
                if(i1 > -1 && i1 < 20 && j1 > -1 && j1 < 20 && (j1 != j || i1 != i))
                    if(map[i1][j1].getType() == 'A')
                        pos.add(new int[]{i1, j1});
                    else if(map[i1][j1].getType() == 'R')
                        posRabbit.add(new int[]{i1, j1});

        if(!posRabbit.isEmpty())
        {
            int o = (int)(Math.random() * posRabbit.size());
            tmp[0] = posRabbit.get(o)[0];
            tmp[1] = posRabbit.get(o)[1];
        }
        else if(!pos.isEmpty())
        {
            int o = (int)(Math.random() * pos.size());
            tmp[0] = pos.get(o)[0];
            tmp[1] = pos.get(o)[1];
        }
        else
        {
            tmp[0] = i;
            tmp[1] = j;
        }


        return tmp;
    }

    private float hungry;
}
