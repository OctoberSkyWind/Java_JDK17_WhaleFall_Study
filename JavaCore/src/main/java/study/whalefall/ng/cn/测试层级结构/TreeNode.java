package study.whalefall.ng.cn.测试层级结构;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 树形结构节点类，用于表示具有层级关系的数据。
 * 该类实现了Serializable接口，以便节点对象可以被序列化。
 */
@Data
public class TreeNode implements Serializable {


    /**
     * 节点的唯一标识ID。
     */
    private Long id;

    /**
     * 节点的名称。
     */
    private String name;

    /**
     * 节点的父节点ID，用于建立节点间的层级关系。
     */
    private Long parentId;

    /**
     * 节点的子节点列表，用于表示节点的层级结构。
     */
    private List<TreeNode> children;

    /**
     * 构造函数，初始化节点的基本信息。
     *
     * @param id     节点的唯一标识ID。
     * @param name  节点的名称或标签。
     * @param parentId 节点的父节点ID。
     */
    public TreeNode(Long id, String name, Long parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

}
