package ClassSub;

public class Class176<T>
{
    private String text;
    private Class83<T> listener;
    private T object;
    
    
    public Class176(final String text, final Class83<T> listener, final T object) {
        this.text = text;
        this.listener = listener;
        this.object = object;
    }
    
    public String getText() {
        return this.text;
    }
    
    public void setText(final String text) {
        this.text = text;
    }
    
    public Class83<T> getListener() {
        return this.listener;
    }
    
    public void setListener(final Class83<T> listener) {
        this.listener = listener;
    }
    
    public T getObject() {
        return this.object;
    }
    
    public void setObject(final T object) {
        this.object = object;
    }
    
    public void press() {
        if (this.listener != null) {
            this.listener.onClick(this);
        }
    }
    
    public interface Class83<T>
    {
        public static final boolean 请你掂量�?下自己的分量再跟我在这里对话�?会装的好像我的语�?对你无关痛痒好吗我不想再和残废人墨迹了你懂吗还在那摇摆不定是吗你现在的语�?已经没有任何意义你开始对我死缠烂打毫无任何的意义NMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSL �?��?��?��?��?��?��?��??;
        
        void onClick(final Class176<T> p0);
    }
}
