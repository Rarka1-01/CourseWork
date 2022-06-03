package main.java.Client;

public class Animal
{
    Animal()
    {
        this.haveMove = false;
    }

    public void eat() {

    }
    public float currentHungry(){return -1;}
    public boolean isDeath()
    {
        return false;
    }

    public void noMove()
    {
        this.haveMove = false;
    }

    public void yesMove()
    {
        this.haveMove = true;
    }

    public boolean isMove()
    {
        return this.haveMove;
    }

    public char getType()
    {
        return 'A';
    }

    public int[] move(int i, int j, Animal[][] map)
    {
        int[] tmp = new int[2];
        tmp[0] = i;
        tmp[1] = j;
        return tmp;
    }

    protected boolean haveMove;
}
