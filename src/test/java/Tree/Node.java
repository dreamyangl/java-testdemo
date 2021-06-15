package Tree;

/**
 * 二叉树： 每个节点最多有两个叶子节点。
 * 完全二叉树： 叶节点只能出现在最下层和次下层，并且最下面一层的结点都集中在该层最左边的若干位置的二叉树。
 * 平衡二叉树： 左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树。
 */
public class Node {
    public  int iData;
    public double dData;
    public Node leftNode;
    public Node rightNode;
    private Node root;
    //显示树节点信息
    public void showNode()
    {
        System.out.println("{ "+iData+","+dData+" }");
    }

    //插入
    public void insert(int iData,double dData )
    {
        //创建node节点
        Node newNode=new Node();
        newNode.iData=iData;
        newNode.dData=dData;
        //判断root node是否为null
        if(root==null)
        {
            root=newNode;
        }
        //不为null
        else
        {
            Node current=root;
            Node parent;
            while(true)
            {
                parent=current;//保存当current变为null之前的那一个父节点
                if(iData<current.iData)//插入左节点
                {
                    current=current.leftNode;//不断向左node寻找是否为null
                    if(current==null)
                    {
                        parent.leftNode=newNode;
                        return;
                    }

                }
                //插入右节点
                else
                {
                    current=current.rightNode;
                    if(current==null)
                    {
                        parent.rightNode=newNode;
                        return;
                    }
                }

            }

        }
    }

    //查找
    public Node find(int key)
    {
        Node current=root;
        while(current.iData!=key)
        {
            if(current.iData>key)
            {
                current=current.leftNode;
            }else
            {
                current=current.rightNode;
            }
            if(current==null)
                return null;
        }
        return current;
    }

    //查找树中的最大值和最小值
    //最小值存在于一棵树的最下层的最左node
    //最大值存在于一棵树的最下层的最右node
    public Node[] mVal()
    {
        Node minNode=null;
        Node maxNode=null;
        Node[] maxminVal=new Node[2];
        Node current=root;//从树的顶部开始搜索
        while(current!=null)
        {
            minNode=current;
            current=current.leftNode;
        }
        maxminVal[0]=minNode;
        current=root;
        while(current!=null)
        {
            maxNode=current;
            current=current.rightNode;
        }
        maxminVal[1]=maxNode;
        return maxminVal;
    }
}
