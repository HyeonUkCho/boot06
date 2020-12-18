package com.example.boot06.persistence;

import com.example.boot06.domain.WebBoard;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class WebBoardRepositoryTest {

    @Autowired
    WebBoardRepository repo;

    @Test
    public void insertWebBoardDummies() {
        IntStream.range(0,300).forEach( i -> {
            WebBoard board = new WebBoard();
            board.setTitle("Sample Board Title " + i);
            board.setContent("Sample Board content " + i);
            board.setWriter("user0" + (i%10));

            repo.save(board);
        });
    }
}
