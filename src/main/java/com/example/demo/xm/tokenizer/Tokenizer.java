package com.example.demo.xm.tokenizer;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.io.IOUtil;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.xm.tokenizer.ReadTxtFile.readTxt;


/**
 * 对中文分词的封装，封装了对Xmnlp（xuming对HanLP改进版）的调用
 * 对分词器的调用采用了单例模式，实现需要时的延迟加载。
 *
 * @author xuming
 */
public class Tokenizer {
    private static final Logger logger = LoggerFactory.getLogger(Tokenizer.class);

    public static List<Word> segment(String sentence) {
        List<Word> results = new ArrayList<>();
        /*// ansj_seg
        List<org.xm.ansj.domain.Term> termList = StandardSegmentation.parse(sentence).getTerms();//ansj
        results.addAll(termList
                .stream()
                .map(term -> new Word(term.getName(), term.getNature().natureStr))
                .collect(Collectors.toList())
        );*/

        /*//Xmnlp
        List<org.xm.xmnlp.seg.domain.Term> termList = Xmnlp.segment(sentence);
        results.addAll(termList
                .stream()
                .map(term -> new Word(term.word, term.getNature().name()))
                .collect(Collectors.toList())
        );*/

        // HanLP

        //readTxt("src/main/resources/wuliucihui.txt");

        List<Term> termList = HanLP.segment(sentence);
        results.addAll(termList
                .stream()
                .map(term -> new Word(term.word))
                .collect(Collectors.toList())
        );

        return results;
    }



    public static void main(String[] args) {


        System.out.println(segment("伸手党一点好处都没有"));
        String content = "1，有限的确定性算法。这类算法在有限的一段时间内终止。2，有限的非确定算法。这类算法在有限的时间内终止。3，无限的算法。是那些由于没有定义终止定义条件，或输入的数据满足而不终止运行的算法。";
        List<String> keywordList = HanLP.extractKeyword(content, 5);
        System.out.println(keywordList);
    }
}
