package com.evansitzes.vocabularyflashcards.model;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by evan on 9/20/15.
 */
public class KoreanWords {
    private HashMap<String, String> koreanToEnglish = new HashMap<String, String>();

    public void populateInitialWordlist() {
        koreanToEnglish.put("것 ", "A thing or an object");
        koreanToEnglish.put("주다", "  to give");
        koreanToEnglish.put("듣다", "  to hear");
        koreanToEnglish.put("배우다", "to learn");
        koreanToEnglish.put("씻다", "to wash");
        koreanToEnglish.put("쓰다", "to write");
        koreanToEnglish.put("울다", "to cry");
        koreanToEnglish.put("갖다", "to have");
        koreanToEnglish.put("웃다", "to laugh");
        koreanToEnglish.put("보다", "to see");
        koreanToEnglish.put("일어나다", "to get up");
        koreanToEnglish.put("걷다", "to walk");
        koreanToEnglish.put("춤추다", "to dance");
        koreanToEnglish.put("만나다", "to meet");
        koreanToEnglish.put("공부하다", "to study");
        koreanToEnglish.put("운전하다", "to drive");
        koreanToEnglish.put("읽다", "to read");
        koreanToEnglish.put("주문하다", "to order");
        koreanToEnglish.put("입다", "to wear");
        koreanToEnglish.put("찍다", "to take (picture)");
        koreanToEnglish.put("쓰다", "to wear (hat, eyewear)");
        koreanToEnglish.put("신다", "  to wear (shoes, socks, footwear)");
        koreanToEnglish.put("빌리다", "to borrow, lend");
        koreanToEnglish.put("전화하다", "to telephone");
        koreanToEnglish.put("가르치다", "to teach");
        koreanToEnglish.put("기다리다", "to wait");
        koreanToEnglish.put("걸다", "to call, dial");
        koreanToEnglish.put("청소하다", "to clean");
        koreanToEnglish.put("타다", "to ride");
        koreanToEnglish.put("나가다", "to exit");
        koreanToEnglish.put("들어오다", "to enter");
        koreanToEnglish.put("물어보다", "to ask");
        koreanToEnglish.put("필요하다", "to need");
        koreanToEnglish.put("열다", "to open");
        koreanToEnglish.put("닫다", "to close");
        koreanToEnglish.put("일하다", "to work");
        koreanToEnglish.put("쉬다", "to rest");
        koreanToEnglish.put("생각하다", "to think");
        koreanToEnglish.put("알다", "to know");
        koreanToEnglish.put("모르다", "to not know");
        koreanToEnglish.put("요리하다", "to cook");
        koreanToEnglish.put("끓이다", "to boil");
        koreanToEnglish.put("튀기다", "to deep fry");
        koreanToEnglish.put("재다", "to measure, weigh");
        koreanToEnglish.put("섞다", "to mix, blend");
        koreanToEnglish.put("볶다", "to fry");
        koreanToEnglish.put("찌다", "to steam");
        koreanToEnglish.put("휘젓다", "to stir");
        koreanToEnglish.put("하다", "to do");
        koreanToEnglish.put("있다", "to have");
        koreanToEnglish.put("연습하다", "to practice");
        koreanToEnglish.put("묻다", "to ask");
        koreanToEnglish.put("내다", "to pay");
        koreanToEnglish.put("살다", "to live");
        koreanToEnglish.put("태어나다", "to be born");
        koreanToEnglish.put("좋아하다", "to like");
        koreanToEnglish.put("결혼하다", "to marry");
        koreanToEnglish.put("걱정하다", "to worry");
        koreanToEnglish.put("약속하다", "to promise");
        koreanToEnglish.put("거짓말하다", "to lie");
        koreanToEnglish.put("고백하다", "to confess");
        koreanToEnglish.put("죄송하다", "to be sorry");
        koreanToEnglish.put("찾다", "to find, to look for");
        koreanToEnglish.put("가지다", "to have");
        koreanToEnglish.put("기억하다", "to remember");
        koreanToEnglish.put("꿈꾸다", "to dream");
        koreanToEnglish.put("시작하다", "to start");
        koreanToEnglish.put("끝나다", "to finish");
        koreanToEnglish.put("보내다", "to send");
        koreanToEnglish.put("팔다", "to sell");
        koreanToEnglish.put("싸우다", "to fight");
        koreanToEnglish.put("대답하다", "to answer");
        koreanToEnglish.put("소개하다", "to introduce");
        koreanToEnglish.put("출발하다", "to depart");
        koreanToEnglish.put("도착하다", "to arrive");
        koreanToEnglish.put("벗다", "to undress, take off clothes");
        koreanToEnglish.put("이기다", "to win, defeat");
        koreanToEnglish.put("지다", "to lose, be defeated");
        koreanToEnglish.put("서두르다", "to hurry, rush");
        koreanToEnglish.put("사랑에 빠지다", "to fall in love");
    }

    public HashMap<String, String> getWordList() {
        return koreanToEnglish;
    }

    public int getSize() {
        return koreanToEnglish.size();
    }

    public String getRandomKoreanWord() {
        Random generator = new Random();
        Object[] words = koreanToEnglish.keySet().toArray();
        Object randomValue = words[generator.nextInt(words.length)];

        return randomValue.toString();
    }

    public String getEnglishFromKorean(String korean) {
        return koreanToEnglish.get(korean);
    }

    public void removeKoreanWord(String korean) {
        koreanToEnglish.remove(korean);
    }

    public void setWordList(HashMap<String, String> koreanToEnglish) {
        this.koreanToEnglish = koreanToEnglish;
    }
}
