package com.github.andriiyan.sprongtraining.impl.dao;

import com.github.andriiyan.sprongtraining.api.model.Identifierable;
import com.github.andriiyan.sprongtraining.impl.utils.file.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collection;

@RunWith(MockitoJUnitRunner.class)
public class BaseDaoImplTest extends BaseDaoImpl<DummyTestModel> {

    public BaseDaoImplTest() {
        super();
    }

    @Before
    public void init() {
        clean();
    }

    @Test
    public void save_should_generate_id_and_save_it() {
        DummyTestModel dummyTestModel = new DummyTestModel(-1, 1, "test");
        DummyTestModel savedModel = save(dummyTestModel);
        Assert.assertEquals(1, savedModel.getId());
        Assert.assertEquals(dummyTestModel.getTest(), savedModel.getTest());
        Assert.assertEquals(dummyTestModel.getTestString(), savedModel.getTestString());

        DummyTestModel foundModel = findById(1);
        Assert.assertEquals(savedModel, foundModel);

        DummyTestModel savedModel2 = save(dummyTestModel);
        Assert.assertEquals(2, savedModel2.getId());
    }

    @Test
    public void delete_should_remove_model() {
        DummyTestModel savedModel1 = save(new DummyTestModel(-1, 1, "test1"));
        DummyTestModel savedModel2 = save(new DummyTestModel(-1, 2, "test2"));
        Assert.assertEquals(savedModel1, findById(1));
        Assert.assertEquals(savedModel2, findById(2));
        boolean removed = delete(savedModel1.getId());
        Assert.assertTrue(removed);
        Assert.assertEquals(savedModel2, findById(2));
        Assert.assertNull(findById(1));
    }

    @Test
    public void delete_should_return_false_if_instance_not_persists_in_storage() {
        Assert.assertFalse(delete(123));
    }

    @Test
    public void update_should_return_null_if_instance_not_persists_in_storage() {
        Assert.assertNull(update(new DummyTestModel(-1, 1, "test1")));
    }

    @Test
    public void findById_should_return_null_if_instance_not_persists_in_storage() {
        Assert.assertNull(findById(123));
    }

    @Test
    public void findById_should_return_correct_model() {
        DummyTestModel savedModel = save(new DummyTestModel(-1, 2, "test2"));
        Assert.assertEquals(savedModel, findById(1));
    }

    @Test
    public void update_should_update_instance_properly() {
        DummyTestModel savedModel = save(new DummyTestModel(-1, 2, "test2"));
        Assert.assertEquals(savedModel, findById(1));
        DummyTestModel toUpdateModel = new DummyTestModel(savedModel.getId(), 123123, "new value");
        DummyTestModel updatedModel = update(toUpdateModel);
        Assert.assertEquals(toUpdateModel, updatedModel);
        Assert.assertEquals(1, findAll().size());
    }

    @Test
    public void clean_should_remove_all_instances() {
        DummyTestModel savedModel1 = save(new DummyTestModel(-1, 1, "test1"));
        DummyTestModel savedModel2 = save(new DummyTestModel(-1, 2, "test2"));
        Assert.assertEquals(savedModel1, findById(1));
        Assert.assertEquals(savedModel2, findById(2));
        Assert.assertEquals(2, findAll().size());
        clean();
        Assert.assertNull(findById(1));
        Assert.assertNull(findById(2));
        Assert.assertEquals(0, findAll().size());
    }

    @Test
    public void findAll_should_return_all_persist_models() {
        Assert.assertEquals(0, findAll().size());
        DummyTestModel savedModel1 = save(new DummyTestModel(-1, 1, "test1"));
        DummyTestModel savedModel2 = save(new DummyTestModel(-1, 2, "test2"));
        Assert.assertEquals(savedModel1, findById(1));
        Assert.assertEquals(savedModel2, findById(2));
        Collection<DummyTestModel> allSavedObjects = findAll();
        Assert.assertEquals(2, allSavedObjects.size());
        Assert.assertTrue(allSavedObjects.contains(savedModel1));
        Assert.assertTrue(allSavedObjects.contains(savedModel2));
    }

    @Test
    public void initialize_should_be_invoked_if_path_and_utils_are_persists() {
        BaseDaoImplTest spied = Mockito.spy(this);
        // should not be invoked because not all conditions are satisfied
        spied.initialize();
        Mockito.verify(spied, Mockito.times(0)).initialize(Mockito.any());

        // should not be invoked because not all conditions are satisfied
        spied.setInitializationFilePath("test");
        spied.initialize();
        Mockito.verify(spied, Mockito.times(0)).initialize(Mockito.any());

        // should not be invoked because not all conditions are satisfied
        spied.setInitializationFilePath("");
        spied.setFileUtils(Mockito.mock(FileUtils.class));
        spied.initialize();
        Mockito.verify(spied, Mockito.times(0)).initialize(Mockito.any());

        spied.setInitializationFilePath("test");
        spied.setFileUtils(Mockito.mock(FileUtils.class));
        spied.initialize();
        Mockito.verify(spied).initialize(Mockito.any());
    }

    @Override
    public Class<DummyTestModel> getType() {
        return DummyTestModel.class;
    }
}

class DummyTestModel implements Identifierable {
    private long id;
    private int test;
    private String testString;

    DummyTestModel(long id, int test, String testString) {
        this.id = id;
        this.test = test;
        this.testString = testString;
    }

    @Override
    public long getId() {
        return id;
    }

    public int getTest() {
        return test;
    }

    public String getTestString() {
        return testString;
    }

    public void setTest(int test) {
        this.test = test;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

    public void setId(long id) {
        this.id = id;
    }
}
