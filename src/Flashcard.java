public class Flashcard {
    private String front;
    private String back;
    private boolean isFront;

    public Flashcard(String front, String back) {
        this.front = front;
        this.back = back;
        isFront = true;
    }

    public void flip() {
        isFront = !isFront;
    }

    public boolean frontUp() {
        return isFront;
    }

    public String getFront() {
        return front;
    }

    public String getBack() {
        return back;
    }

    public void setFront(String newFront) {
        front = newFront;
    }

    public void setBack(String newBack) {
        back = newBack;
    }

    // Front = true
    public void setFrontUp(boolean sideUp) {
        isFront = sideUp;
    }
}